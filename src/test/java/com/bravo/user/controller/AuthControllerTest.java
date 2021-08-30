package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.localization.MessageHandler;
import com.bravo.user.model.dto.AuthDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private static final String CREDENTIALS_INVALID = "credentialsInvalid";
    private static final String USERNAME_OR_PASSWORD_EMPTY = "usernameOrPasswordEmpty";
    private static final String AUTH_TOKEN_PATH = "/auth/token";
    private static final String BEARER_TOKEN = "Bearer";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Jackson2ObjectMapperBuilder objectMapperBuilder;

    @Autowired
    private MessageHandler messageHandler;

    @Test
    void authenticateInvalidEmail() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("Joyce-Roberts@gmail.comX", "UserPassword")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(messageHandler.localizeMessage(CREDENTIALS_INVALID)));
    }

    @Test
    void authenticateInvalidPassword() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("Joyce-Roberts@gmail.comX", "UserPasswordX")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(messageHandler.localizeMessage(CREDENTIALS_INVALID)));
    }

    @Test
    void authenticateInvalidEmailAndPassword() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("Joyce-Roberts@gmail.comX", "UserPasswordX")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(messageHandler.localizeMessage(CREDENTIALS_INVALID)));
    }

    @Test
    void authenticateInvalidEmptyEmail() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("", "UserPassword")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(messageHandler.localizeMessage(USERNAME_OR_PASSWORD_EMPTY)));
    }

    @Test
    void authenticateInvalidEmptyPassword() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("Joyce-Roberts@gmail.com", "")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(messageHandler.localizeMessage(CREDENTIALS_INVALID)));
    }

    @Test
    void authenticateInvalidEmptyEmailAndPassword() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("", "")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(messageHandler.localizeMessage(USERNAME_OR_PASSWORD_EMPTY)));
    }

    @Test
    void authenticateValid() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("Frederick-Cole@gmail.com", "FooBar!")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessType").value(messageHandler.localizeMessage(BEARER_TOKEN)));
    }

    @Test
    void authenticateValidAdmin() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("Joyce-Roberts@gmail.com", "UserPassword")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessType").value(messageHandler.localizeMessage(BEARER_TOKEN)));
    }

    @Test
    void authenticateValidCaseInsensitive() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("cadie-hall@gmail.com", "password")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessType").value(messageHandler.localizeMessage(BEARER_TOKEN)));
    }

    @Test
    void authenticateValidAdminCaseInsensitive() throws Exception {
        mockMvc.perform(post(AUTH_TOKEN_PATH)
                        .content(objectMapperBuilder.build().writeValueAsString(new AuthDto("joyce-roberts@gmail.com", "UserPassword")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessType").value(messageHandler.localizeMessage(BEARER_TOKEN)));
    }
}