package com.bravo.user.security;

import com.bravo.user.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class acts as the auth provider, which is used set the user details as well as the password encoder settings
 */
public class AuthProvider extends DaoAuthenticationProvider {

    private final AuthHandler authHandler;

    public AuthProvider(AuthHandler authHandler, AuthService authService, PasswordEncoder encoder) {
        this.authHandler = authHandler;
        this.setUserDetailsService(authService);
        this.setPasswordEncoder(encoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final Authentication result = authHandler.authenticate(authentication);
        return new UsernamePasswordAuthenticationToken(authentication, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
