package com.bravo.user.security;

import com.bravo.user.config.AuthConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class acts as the jwt token provider, which is uses the auth config settings to generate an auth token
 */
@Component
public class JwtTokenProvider {

    private final AuthConfig authConfig;

    @Autowired
    public JwtTokenProvider(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    /**
     * Uses an authentication object to generate an auth token
     *
     * @param authentication authentication object
     * @return auth token
     */
    public String generateToken(Authentication authentication) {
        var issueDate = new Date();
        var expireDate = new Date(issueDate.getTime() + authConfig.getJwtExpirationInMs());

        //build and return the jwt token
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, authConfig.getJwtSecret())
                .compact();
    }
}
