package com.formation.formationAPI.service;

import com.formation.formationAPI.models.DTO.UtilisateurDTO;
import com.formation.formationAPI.models.payload.JwtResponse;
import com.formation.formationAPI.models.request.LoginRequest;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.models.request.SignupSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AuthService {

    @Transactional(propagation = Propagation.REQUIRED)
    JwtResponse signup(SignupSaveRequest signupSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    JwtResponse login( LoginRequest loginRequest);
}
