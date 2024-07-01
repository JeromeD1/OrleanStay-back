package com.formation.orleanStay.service;

import com.formation.orleanStay.models.payload.JwtResponse;
import com.formation.orleanStay.models.request.LoginRequest;
import com.formation.orleanStay.models.request.SignupSaveRequest;
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
