package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.mapper.PersonalInformationMapper;
import com.formation.formationAPI.mapper.UtilisateurMapper;
import com.formation.formationAPI.models.DTO.UtilisateurDTO;
import com.formation.formationAPI.models.entity.PersonalInformation;
import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.payload.JwtResponse;
import com.formation.formationAPI.models.request.LoginRequest;
import com.formation.formationAPI.models.request.SignupSaveRequest;
import com.formation.formationAPI.repository.PersonalInformationRepository;
import com.formation.formationAPI.repository.UtilisateurRepository;
import com.formation.formationAPI.service.AuthService;
import com.formation.formationAPI.utils.Findbyid;
import com.formation.formationAPI.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    final private UtilisateurRepository utilisateurRepository;
    final private PersonalInformationRepository personalInformationRepository;
    final private UtilisateurMapper utilisateurMapper;
    final private PersonalInformationMapper personalInformationMapper;
    final private Findbyid findbyid;
    final private BCryptPasswordEncoder passwordEncoder;
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

    private boolean verifyPassword(String requestPassword, String hashedBddPassword) {
        return passwordEncoder.matches(requestPassword, hashedBddPassword);
    }

}
