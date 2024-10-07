package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.PersonalInformationMapper;
import com.formation.orleanStay.mapper.UtilisateurMapper;
import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.payload.JwtResponse;
import com.formation.orleanStay.models.request.LoginRequest;
import com.formation.orleanStay.models.request.SignupSaveRequest;
import com.formation.orleanStay.repository.PersonalInformationRepository;
import com.formation.orleanStay.repository.UtilisateurRepository;
import com.formation.orleanStay.service.AuthService;
import com.formation.orleanStay.utils.Findbyid;
import com.formation.orleanStay.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
