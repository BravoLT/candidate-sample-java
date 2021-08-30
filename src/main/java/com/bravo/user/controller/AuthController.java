package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.AuthDto;
import com.bravo.user.model.dto.AuthTokenDto;
import com.bravo.user.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST controller integrated with swagger used for for auth endpoints
 */
@RequestMapping(value = "/auth")
@SwaggerController
public class AuthController {

    private static final String BEARER_TOKEN = "Bearer";
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(@Qualifier(BeanIds.AUTHENTICATION_MANAGER) AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Uses auth credentials to authenticate and generate an auth token
     *
     * @param request auth request
     * @return response entity
     */
    @PostMapping("/token")
    public ResponseEntity<Object> authenticate(@RequestBody AuthDto request) {
        //authenticates a user
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //returns the user access token
        return ResponseEntity.ok().body(new AuthTokenDto(BEARER_TOKEN, jwtTokenProvider.generateToken(authentication)));
    }

}
