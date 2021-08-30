package com.bravo.user.service;

import com.bravo.user.dao.repository.AuthRepository;
import com.bravo.user.localization.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as the service layer, which is used to fetch auth data from the db by calling auth repo methods
 */
@Service
@Slf4j
public class AuthService implements UserDetailsService {

    private static final String CREDENTIALS_INVALID = "credentialsInvalid";
    private static final String DEFAULT_ROLE = "USER";
    private final AuthRepository authRepository;
    private final MessageHandler messageHandler;

    @Autowired
    public AuthService(AuthRepository authRepository, MessageHandler messageHandler) {
        this.authRepository = authRepository;
        this.messageHandler = messageHandler;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        //case-insensitive search for an auth object by email
        var auth = authRepository.findByEmailIgnoreCase(email);
        log.debug("fetched auth for email '{}'", email);
        if (auth == null) {
            log.error(messageHandler.localizeMessage(CREDENTIALS_INVALID));
            throw new BadCredentialsException(messageHandler.localizeMessage(CREDENTIALS_INVALID));
        }
        //default user role, if no role value is stored for a user in the db (ADMIN or default USER)
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(auth.getRole() != null ? String.valueOf(auth.getRole()) : DEFAULT_ROLE));
        return new User(auth.getEmail(), auth.getPassword(), true, true, true, true, grantedAuths);
    }
}
