package com.formation.formationAPI.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "orleans.stay.app.jwt", ignoreUnknownFields = false)
public class JwtProperties {
    private String secret;

    private int expirationMs;

//    private int refreshExpirationMs;
}