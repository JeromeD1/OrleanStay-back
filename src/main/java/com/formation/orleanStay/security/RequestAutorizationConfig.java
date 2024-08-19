package com.formation.orleanStay.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;

import static com.formation.orleanStay.models.enumeration.ERole.Constants.ADMIN;
import static com.formation.orleanStay.models.enumeration.ERole.Constants.OWNER;

@Configuration
public class RequestAutorizationConfig {

    private static final String APPARTMENT_ENDPOINT = "/appartment/**";
    private static final String DISCOUNT_ENDPOINT = "/discount/**";
    private static final String FEEDBACK_ENDPOINT = "/feedback/**";
    private static final String FEEDBACK_ANSWER_ENDPOINT = "/feedbackAnswer/**";
    private static final String INFO_ENDPOINT = "/info/**";
    private static final String PERSONAL_INFORMATION_ENDPOINT = "/personalInformation/**";
    private static final String PHOTO_ENDPOINT = "/photo/**";
    private static final String PRIVATE_PHOTO_ENDPOINT = "/privatePhoto/**";
    private static final String RESERVATION_ENDPOINT = "/reservation/**";
    private static final String RESERVATION_CHAT_ENDPOINT = "/reservationChat/**";
    private static final String TRAVELLER_ENDPOINT = "/traveller/**";
    private static final String UTILISATEUR_ENDPOINT = "/utilisateurs/**";

    /**
     * Configuration des permissions suivant les différents appels de routes
     * @return {@Link Customizer}
     */
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer() {
        return authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()

                .requestMatchers("/error").permitAll()
                .requestMatchers(POST, "/signup").permitAll()
                .requestMatchers(POST, "/login").permitAll()
                .requestMatchers(POST, "/logMeOut").permitAll()

                .requestMatchers(GET, "/appartment/active").permitAll()
                .requestMatchers(GET, "/appartment/all").hasRole(ADMIN)
                .requestMatchers(GET, "/appartment/owner/**").hasAnyRole(OWNER, ADMIN)
                .requestMatchers(GET, "/appartment/names/**").hasAnyRole(OWNER, ADMIN)
                .requestMatchers(GET, APPARTMENT_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(POST, APPARTMENT_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(PUT, APPARTMENT_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(DELETE, APPARTMENT_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, PRIVATE_PHOTO_ENDPOINT).authenticated()
                .requestMatchers(POST, PRIVATE_PHOTO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(PUT, PRIVATE_PHOTO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(DELETE, PRIVATE_PHOTO_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, DISCOUNT_ENDPOINT).authenticated()
                .requestMatchers(POST, DISCOUNT_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(PUT, DISCOUNT_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(DELETE, DISCOUNT_ENDPOINT).hasAnyRole(ADMIN)

                .requestMatchers(GET, FEEDBACK_ENDPOINT).permitAll()
                .requestMatchers(POST, FEEDBACK_ENDPOINT).authenticated()
                .requestMatchers(PUT, FEEDBACK_ENDPOINT).authenticated()
                .requestMatchers(DELETE, FEEDBACK_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, FEEDBACK_ANSWER_ENDPOINT).authenticated()
                .requestMatchers(POST, FEEDBACK_ANSWER_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(PUT, FEEDBACK_ANSWER_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(DELETE, FEEDBACK_ANSWER_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, INFO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(POST, INFO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(PUT, INFO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(DELETE, INFO_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, PERSONAL_INFORMATION_ENDPOINT).authenticated()
                .requestMatchers(POST, PERSONAL_INFORMATION_ENDPOINT).authenticated()
                .requestMatchers(PUT, PERSONAL_INFORMATION_ENDPOINT).authenticated()
                .requestMatchers(DELETE, PERSONAL_INFORMATION_ENDPOINT).hasAnyRole(ADMIN)

                .requestMatchers(GET, PHOTO_ENDPOINT).authenticated()
                .requestMatchers(POST, PHOTO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
                .requestMatchers(PUT, PHOTO_ENDPOINT).hasAnyRole(OWNER, ADMIN)
//                .requestMatchers(PUT, "/photo/updateOrder/**").hasAnyRole(OWNER, ADMIN)
                .requestMatchers(DELETE, "/photo/{id}/imgId/**").hasAnyRole(OWNER, ADMIN)
//                .requestMatchers(DELETE, PHOTO_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, "/reservation/requests/all").hasAnyRole(ADMIN)
                .requestMatchers(GET, "/reservation/requests/**").hasAnyRole(OWNER, ADMIN)
                .requestMatchers(GET, RESERVATION_ENDPOINT).authenticated()
                .requestMatchers(POST, RESERVATION_ENDPOINT).permitAll()
                .requestMatchers(PUT, RESERVATION_ENDPOINT).authenticated()
                .requestMatchers(DELETE, RESERVATION_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, RESERVATION_CHAT_ENDPOINT).authenticated()
                .requestMatchers(POST, RESERVATION_CHAT_ENDPOINT).authenticated()
                .requestMatchers(PUT, RESERVATION_CHAT_ENDPOINT).authenticated()
                .requestMatchers(DELETE, RESERVATION_CHAT_ENDPOINT).hasAnyRole(OWNER, ADMIN)

                .requestMatchers(GET, TRAVELLER_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(POST, TRAVELLER_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(PUT, TRAVELLER_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(DELETE, TRAVELLER_ENDPOINT).hasAnyRole(ADMIN)

                .requestMatchers(GET, UTILISATEUR_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(POST, UTILISATEUR_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(PUT, UTILISATEUR_ENDPOINT).hasAnyRole(ADMIN)
                .requestMatchers(DELETE, UTILISATEUR_ENDPOINT).hasAnyRole(ADMIN)

                .requestMatchers(GET, "/cloudinary/signature").hasAnyRole(ADMIN, OWNER)



                .anyRequest().permitAll();
    }


}
