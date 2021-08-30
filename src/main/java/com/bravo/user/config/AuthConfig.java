package com.bravo.user.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This class is used to store the auth settings passed in from the app yaml config files
 */
@Data
@ConfigurationProperties(prefix = "auth.config")
public class AuthConfig {

    private Integer jwtExpirationInMs;
    private String jwtSecret;
}
