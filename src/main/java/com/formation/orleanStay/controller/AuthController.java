package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.payload.JwtResponse;
import com.formation.orleanStay.models.request.LoginRequest;
import com.formation.orleanStay.models.request.RecoveryTokenRequest;
import com.formation.orleanStay.models.request.ReinitialisationPasswordSaveRequest;
import com.formation.orleanStay.models.request.SignupSaveRequest;
import com.formation.orleanStay.service.AuthService;
import com.formation.orleanStay.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private AuthService authService;
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public JwtResponse signup(@RequestBody SignupSaveRequest signupSaveRequest, HttpServletResponse response) {
        log.info("Signup and add new user from signupSaveRequest {}", signupSaveRequest);
        final ResponseCookie cookie = jwtUtils.setRefreshTokenCookie(signupSaveRequest.getLogin());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return authService.signup(signupSaveRequest);
    }


    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Login de l'utilisateur suivant loginRequest = {}", loginRequest);
        final ResponseCookie cookie = jwtUtils.setRefreshTokenCookie(loginRequest.getLogin());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return authService.login(loginRequest);
    }

    @PostMapping("/logMeOut")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {

        try {
            log.info("Logout de l'utilisateur");
            authService.logout();
            final ResponseCookie cookie = authService.deleteCookie(response);
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Logout successful");
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            log.error("Error during logout", e);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Logout failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping("/askForReinitializingPassword")
    @ResponseStatus(HttpStatus.OK)
    public boolean askForReinitializingPassword(@RequestBody String email){
        return authService.askForReinitializingPassword(email);
    }

    @PostMapping("/reinitialisePassword")
    @ResponseStatus(HttpStatus.OK)
    public boolean askForReinitializingPassword(@RequestBody ReinitialisationPasswordSaveRequest request){
        return authService.reinitialisePassword(request);
    }

    @GetMapping("/verifySessionActivity")
    public boolean verifySessionActivity() {
        log.info("Verify session activity, return true if user authenticated");
        return true;
    }
}
