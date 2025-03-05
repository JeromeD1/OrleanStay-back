package com.formation.orleanStay.security;

import com.formation.orleanStay.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final CustomUserDetailsService customUserDetailsService;


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String accessJWT = parseJwt(request);
//            if (accessJWT != null && jwtUtils.validateJwtToken(accessJWT)) {
//                String login = jwtUtils.getLoginFromJwtToken(accessJWT);
//
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            log.error("Cannot set user authentication: {}", e);
//        }
//
//        filterChain.doFilter(request, response);
//    }
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    // Permettre aux requêtes OPTIONS de passer sans authentification
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String accessJWT = parseJwt(request);
        log.info("Access JWT control");

        if (accessJWT != null && jwtUtils.validateJwtToken(accessJWT) && compareCookieWithAccessJwt(request, accessJWT)) {
            log.info("Access JWT OK");
            String login = jwtUtils.getLoginFromJwtToken(accessJWT);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    } catch (Exception e) {
        log.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
}


    /**
     * Retrieve 'Authorization' token from the request
     *
     * @param request request
     * @return token if it exists, null otherwise
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    private boolean compareCookieWithAccessJwt(HttpServletRequest request, String accessJWT) {
//        System.out.println("accessJWT: " + accessJWT);
//        //comparaison de accessJWT avec le cookie pour double vérification
//        Cookie[] cookies = request.getCookies();
//        System.out.println("cookies: " + cookies);
//        for(Cookie cookie : cookies) {
//            if("refreshToken".equals(cookie.getName())) {
//                String cookieToken = cookie.getValue();
//                if(cookieToken.equals(accessJWT)) {
//                    System.out.println("cookieToken: " + cookieToken);
//                    return true;
//                }
//            }
//        }
//        return false;
        //TODO: remettre le code ci dessus quand compris pourquoi cookie non envoyés
        return true;
    }
}
