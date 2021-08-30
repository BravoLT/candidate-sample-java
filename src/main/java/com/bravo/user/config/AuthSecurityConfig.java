package com.bravo.user.config;

import com.bravo.user.localization.MessageHandler;
import com.bravo.user.security.AuthHandler;
import com.bravo.user.security.AuthProvider;
import com.bravo.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class is used to secure the application using spring security configuration
 */
@Configuration
@EnableWebSecurity
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private final MessageHandler messageHandler;

    @Autowired
    public AuthSecurityConfig(AuthService authService, MessageHandler messageHandler) {
        this.authService = authService;
        this.messageHandler = messageHandler;
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        var encoder = new BCryptPasswordEncoder(11);
        var authHandler = new AuthHandler(authService, encoder, messageHandler);
        auth.authenticationProvider(new AuthProvider(authHandler, authService, encoder));
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/h2/**", "/swagger-resources/**", "/swagger-ui.html");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
