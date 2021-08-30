package com.bravo.user.security;

import com.bravo.user.localization.MessageHandler;
import com.bravo.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * This class acts as the auth handler, which is used to authenticate using a user credentials
 */
@Slf4j
public class AuthHandler {

    private static final String CREDENTIALS_INVALID = "credentialsInvalid";
    private static final String USERNAME_OR_PASSWORD_EMPTY = "usernameOrPasswordEmpty";
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final MessageHandler messageHandler;

    public AuthHandler(AuthService authService, PasswordEncoder passwordEncoder, MessageHandler messageHandler) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.messageHandler = messageHandler;
    }

    /**
     * Authenticates a user after fetching the user details for the given credentials
     *
     * @param authentication auth object
     *
     * @return authentication results for a given users credentials
     */
    public Authentication authenticate(Authentication authentication) {

        //throw an exception if the username or password are empty
        if (!StringUtils.hasText(authentication.getName()) || !(authentication.getCredentials() instanceof String)) {
            log.error(messageHandler.localizeMessage(USERNAME_OR_PASSWORD_EMPTY));
            throw new BadCredentialsException(messageHandler.localizeMessage(USERNAME_OR_PASSWORD_EMPTY));
        }

        //retrieve the user details stored in the db
        var userDetails = authService.loadUserByUsername(authentication.getName());

        //throw an exception if passed in password doesn't match the password stored in the db
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            log.error(messageHandler.localizeMessage(CREDENTIALS_INVALID));
            throw new BadCredentialsException(messageHandler.localizeMessage(CREDENTIALS_INVALID));
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

}
