package com.formation.formationAPI.controller;

import com.formation.formationAPI.models.payload.JwtResponse;
import com.formation.formationAPI.models.request.LoginRequest;
import com.formation.formationAPI.models.request.SignupSaveRequest;
import com.formation.formationAPI.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/signup")
    public JwtResponse signup(@RequestBody SignupSaveRequest signupSaveRequest) {
        log.debug("Signup and add new user from signupSaveRequest {}", signupSaveRequest);
        return authService.signup(signupSaveRequest);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        log.debug("Login de l'utilisateur suivant loginRequest = {}", loginRequest);
        return authService.login(loginRequest);
    }
}
