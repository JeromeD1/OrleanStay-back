package com.formation.orleanStay.utils;

import com.formation.orleanStay.security.RefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@EnableConfigurationProperties(JwtProperties.class)
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final String secret;

    private final int expirationMs;

    private final int expirationNewPasswordTokenMs;

//    private final int refreshExpirationMs;

    public JwtUtils(JwtProperties jwtProperties) {
        this.secret = jwtProperties.getSecret();
        this.expirationMs = jwtProperties.getExpirationMs();
        expirationNewPasswordTokenMs = jwtProperties.getExpirationNewPasswordTokenMs();
//        this.refreshExpirationMs = jwtProperties.getRefreshExpirationMs();
    }

    /**
     * Generate an access token with a username
     *
     * @param username used to generate the token
     * @return the access token
     */
    public String generateAccessToken(String username) {
        ClaimsBuilder claims = Jwts.claims();
        claims.subject(username);
        return generateToken(claims.build(), expirationMs);
    }

    /**
     * Generate an reinitialisationPassword token with a email
     *
     * @param email used to generate the token
     * @return the access token
     */
    public String generateReinitialisationPasswordToken(String email) {
        ClaimsBuilder claims = Jwts.claims();
        claims.subject(email);
        return generateToken(claims.build(), expirationNewPasswordTokenMs);
    }

    /**
     * Generate a refresh token with a username
     *
     * @param login used to generate the token
     * @return the {@link RefreshToken}
     */
    public RefreshToken generateRefreshToken(String login) {
        ClaimsBuilder claims = Jwts.claims();
        claims.subject(login);
        return new RefreshToken(generateToken(claims.build(), expirationMs), Duration.ofMillis(expirationMs));
    }

    public ResponseCookie getRefreshTokenCookie(RefreshToken refreshToken) {
        //TODO : passer secure à true
        return ResponseCookie.from("refreshToken", refreshToken.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("none")
                .path("/")
                .maxAge(refreshToken.getExpiryDate())
                .build();
    }

    public ResponseCookie setRefreshTokenCookie(String login) {
        final RefreshToken refreshToken = generateRefreshToken(login);
        return getRefreshTokenCookie(refreshToken);
    }

    /**
     * Generate a token with a username and a list of claims
     * Simplified and called by the other generateToken method
     *
     * @param claims parameter claims
     * @return token
     */
    private String generateToken(Claims claims, int expirationMS) {
        Date now = Date.from(Instant.now());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date((new Date()).getTime() + expirationMS))
                .signWith(secretKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Generates a key used to sign the token
     *
     * @return the {@link Key}
     */
    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * Get login from given token
     *
     * @param token containing the username
     * @return the username
     */
    public String getLoginFromJwtToken(String token) {
        final JwtParser parser = Jwts.parser().verifyWith(secretKey()).build();
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    /**
     * Validate given token
     *
     * @param authToken to be validated
     * @return boolean validating or not the token
     */
    public boolean validateJwtToken(String authToken) {
        final JwtParser parser = Jwts.parser().verifyWith(secretKey()).build();
        try {
            parser.parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public boolean verifyJwtToken(String authToken) {
        final JwtParser parser = Jwts.parser().verifyWith(secretKey()).build();
        //ici je ne vérifie pas les exceptions pour les lever dans la méthode appelante
            parser.parse(authToken);
            return true;
    }
}