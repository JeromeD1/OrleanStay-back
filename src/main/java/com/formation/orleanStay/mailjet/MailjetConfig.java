package com.formation.orleanStay.mailjet;

import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mailjet")
public class MailjetConfig {

    @Value("${mailjet.api.key}")
    private String mailjetApiKey;

    @Value("${mailjet.secret.key}")
    private String mailjetSecretKey;

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(mailjetApiKey, mailjetSecretKey);
    }

}
