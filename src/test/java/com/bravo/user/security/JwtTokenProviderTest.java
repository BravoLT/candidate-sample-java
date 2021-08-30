package com.bravo.user.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.mockito.Mockito.when;

@SpringBootTest
class JwtTokenProviderTest {

    @MockBean
    @Qualifier(BeanIds.AUTHENTICATION_MANAGER)
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private Authentication authentication;

    @Test
    void generateToken() {
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(
                new UsernamePasswordAuthenticationToken("foo", "bar", AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE")));

        String token = jwtTokenProvider.generateToken(authentication);
        Assertions.assertNotNull(token);
    }
}
