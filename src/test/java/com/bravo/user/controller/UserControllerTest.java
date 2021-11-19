package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void retrieveByNameExact() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name=lucy"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Lucy Wells Hill"))
        .andExpect(jsonPath("$[1].name").value("Edith Lucy Watson"))
        .andExpect(jsonPath("$[2].name").value("Lucy Eddy Henderson"));
  }

  @Test
  void retrieveByNameLike() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name=saw*"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Sawyer Richards"))
        .andExpect(jsonPath("$[1].name").value("Sawyer Sullivan"));
  }

  @Test
  void retrieveByNameEmpty() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name="))
        .andExpect(status().isBadRequest());
  }

  @Test
  void retrieveByNameSpecialCharacters() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name=^"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void retrieveByNameSpaceInArgument() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name=Sa w"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void retrieveByNameMissingArgument() throws Exception {
    this.mockMvc.perform(get("/user/retrieve"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void authenticateBadPassword() throws Exception {
    String body =
            "{\n" +
            "    \"email\": \"008a4215-0b1d-445e-b655-a964039cbb5a@gmail.com\",\n" +
            "    \"password\": \"NotJoyce\"\n" +
            "}";
    this.mockMvc.perform(post("/user/authenticate").content(body).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(""));
  }

  @Test
  void authenticateNonExistentEmail() throws Exception {
    String body =
            "{\n" +
            "    \"email\": \"not-an-email@gmail.com\",\n" +
            "    \"password\": \"Joyce\"\n" +
            "}";
    this.mockMvc.perform(post("/user/authenticate").content(body).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(""));
  }

  @Test
  void authenticateHappyPath() throws Exception {
    String body =
            "{\n" +
            "    \"email\": \"008a4215-0b1d-445e-b655-a964039cbb5a@gmail.com\",\n" +
            "    \"password\": \"Joyce\"\n" +
            "}";
    this.mockMvc.perform(post("/user/authenticate").content(body).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
  }
}