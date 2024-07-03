package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.payload.JwtResponse;
import com.formation.orleanStay.models.request.LoginRequest;
import com.formation.orleanStay.models.request.SignupSaveRequest;
import com.formation.orleanStay.service.AuthService;
import com.formation.orleanStay.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private AuthService authService;
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public JwtResponse signup(@RequestBody SignupSaveRequest signupSaveRequest, HttpServletResponse response) {
        log.debug("Signup and add new user from signupSaveRequest {}", signupSaveRequest);
        final ResponseCookie cookie = jwtUtils.setRefreshTokenCookie(signupSaveRequest.getLogin());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return authService.signup(signupSaveRequest);
    }


    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.debug("Login de l'utilisateur suivant loginRequest = {}", loginRequest);
        final ResponseCookie cookie = jwtUtils.setRefreshTokenCookie(loginRequest.getLogin());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return authService.login(loginRequest);
    }
}
