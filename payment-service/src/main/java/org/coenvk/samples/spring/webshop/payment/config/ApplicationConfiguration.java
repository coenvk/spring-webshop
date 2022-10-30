package org.coenvk.samples.spring.webshop.payment.config;

import java.security.SecureRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}