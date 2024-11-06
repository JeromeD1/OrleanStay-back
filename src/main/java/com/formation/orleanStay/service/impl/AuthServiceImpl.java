package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mailjet.EmailService;
import com.formation.orleanStay.mapper.PersonalInformationMapper;
import com.formation.orleanStay.mapper.UtilisateurMapper;
import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.entity.RecoveryToken;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.payload.JwtResponse;
import com.formation.orleanStay.models.request.LoginRequest;
import com.formation.orleanStay.models.request.RecoveryTokenRequest;
import com.formation.orleanStay.models.request.ReinitialisationPasswordSaveRequest;
import com.formation.orleanStay.models.request.SignupSaveRequest;
import com.formation.orleanStay.repository.PersonalInformationRepository;
import com.formation.orleanStay.repository.UtilisateurRepository;
import com.formation.orleanStay.service.AuthService;
import com.formation.orleanStay.service.RecoveryTokenService;
import com.formation.orleanStay.utils.Findbyid;
import com.formation.orleanStay.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PersonalInformationRepository personalInformationRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PersonalInformationMapper personalInformationMapper;
    private final Findbyid findbyid;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final RecoveryTokenService recoveryTokenService;


    @Override
    public JwtResponse signup(SignupSaveRequest signupSaveRequest) {
        //vérification de la présence de l'utilisateur dans la BDD
        if(findbyid.verifyUtilisateurExistByLogin(signupSaveRequest.getLogin())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        //criptage du mot de passe
        signupSaveRequest.setPassword(passwordEncoder.encode(signupSaveRequest.getPassword()));

        //Création de nouvelles personalInformation
        final PersonalInformation personalInformationToSave = personalInformationMapper.fromSignupSaveRequest(signupSaveRequest);
        final PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformationToSave);

        //Création d'un nouvel utilisateur
        final Utilisateur utilisateurToSave = utilisateurMapper.fromSignupSaveRequest(signupSaveRequest);
        utilisateurToSave.setPersonalInformations(savedPersonalInformation);
        final Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateurToSave);
        final UtilisateurDTO savedUtilisateurDTO = utilisateurMapper.toUtilisateurDTO(savedUtilisateur);
        final String token = jwtUtils.generateAccessToken(signupSaveRequest.getLogin());

        return new JwtResponse(token, savedUtilisateurDTO);
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        //vérification de la présence de l'utilisateur dans la BDD
        if(!findbyid.verifyUtilisateurExistByLogin(loginRequest.getLogin())) {
            throw new RuntimeException("Cet identifiant n'existe pas.");
        }
        Utilisateur utilisateur = findbyid.findUtilisateurByLogin(loginRequest.getLogin());
        if(!verifyPassword(loginRequest.getPassword(), utilisateur.getPassword())) {
            throw new RuntimeException("L'identifiant ou le mot de passe n'est pas correct.");
        }

        final String token = jwtUtils.generateAccessToken(loginRequest.getLogin());
        UtilisateurDTO utilisateurDTO= utilisateurMapper.toUtilisateurDTO(utilisateur);

        return new JwtResponse(token, utilisateurDTO);

    }

    @Override
    public void logout(){
        //suppression de l'utilisateur dans le serveur
        SecurityContextHolder.clearContext();

    }

    @Override
    public boolean askForReinitializingPassword(String email) {
        //Rechercher l'utilisateur par email = login
        Utilisateur utilisateur = findbyid.findUtilisateurByLogin(email);
        if(utilisateur == null) {return false;}

        final String token = jwtUtils.generateReinitialisationPasswordToken(email);
        //Sauvegarde d'un objet RecoveryToken
        RecoveryTokenRequest recoveryTokenRequest = new RecoveryTokenRequest(token, utilisateur);
        recoveryTokenService.create(recoveryTokenRequest);

        Map<String, String> variables = emailService.makeEmailReinitialisationPasswordData(token);
        String recipientEmail = utilisateur.getLogin();
        String recipientName = utilisateur.getPersonalInformations().getLastname();
        emailService.sendEmail(recipientEmail,recipientName,"Réinitialisation de votre mot de passe",variables);
        return true;
    }

    @Override
    public  boolean reinitialisePassword(ReinitialisationPasswordSaveRequest request) {
        //vérifier que le token est présent et le récupérer
        RecoveryToken recoveryToken = recoveryTokenService.findByToken(request.getToken());
        //vérifier qu'il n'est pas expiré
        if (!validateAndHandleJwtToken(request.getToken())) { return false; }

        String login = jwtUtils.getLoginFromJwtToken(request.getToken());

        if(!login.equals(recoveryToken.getUtilisateur().getLogin()) || !request.getPassword().equals(request.getConfirmationPassword())) {
            return false;
        } else {
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            recoveryToken.getUtilisateur().setPassword(hashedPassword);
            utilisateurRepository.save(recoveryToken.getUtilisateur());
            recoveryTokenService.delete(recoveryToken.getId());
            return true;
        }
    }

    private boolean verifyPassword(String requestPassword, String hashedBddPassword) {
        return passwordEncoder.matches(requestPassword, hashedBddPassword);
    }

    public ResponseCookie deleteCookie(HttpServletResponse response){
        //suppression du cookie
//        Cookie cookieToRemove = new Cookie("refreshToken", "null");
//        cookieToRemove.setMaxAge(0);
//        cookieToRemove.setPath("/");
//        cookieToRemove.setHttpOnly(true);
//        cookieToRemove.setSecure(true);
//        response.addCookie(cookieToRemove);
        return ResponseCookie.from("refreshToken","null")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
    }

    private boolean validateAndHandleJwtToken(String token) {
        try {
            jwtUtils.verifyJwtToken(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            recoveryTokenService.delete(recoveryTokenService.findByToken(token).getId());
            return false;
        }
    }

}
