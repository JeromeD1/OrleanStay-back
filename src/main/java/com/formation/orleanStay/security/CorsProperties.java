package com.formation.orleanStay.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "web.cors", ignoreUnknownFields = false)
public class CorsProperties {

        private String allowedOrigins;

        private String allowedMethods;

        private Boolean allowCredentials;

}
