package com.bravo.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bravo.user.App;
import com.bravo.user.model.dto.UserValidateDto;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  void validateEmailIsBlank() throws Exception {
    UserValidateDto request = new UserValidateDto();
    request.setEmail("");
    request.setPassword("totallyRealPassword");
    mockMvc.perform(post("/user/validate").content(asJsonString(request)).contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)).andExpect(status().isBadRequest());
  }
  
  @Test
  void validatePasswordIsBlank() throws Exception {
    UserValidateDto request = new UserValidateDto();
    request.setEmail("Email@gmail.com");
    request.setPassword("");
    mockMvc.perform(
        post("/user/validate").content(asJsonString(request)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void validateEmailNotFound() throws Exception {
    UserValidateDto request = new UserValidateDto();
    request.setEmail("Email@gmail.com");
    request.setPassword("totallyRealPassword");
    mockMvc.perform(
        post("/user/validate").content(asJsonString(request)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void validatePasswordInvalid() throws Exception {
    UserValidateDto request = new UserValidateDto();
    request.setEmail("Joyce.Roberts@gmail.com");
    request.setPassword("totallyRealPassword");
    mockMvc.perform(
        post("/user/validate").content(asJsonString(request)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void validateUserValid() throws Exception {
    UserValidateDto request = new UserValidateDto();
    request.setEmail("Joyce.Roberts@gmail.com");
    request.setPassword("passwordOne");
    mockMvc.perform(
        post("/user/validate").content(asJsonString(request)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  public static String asJsonString(final Object obj) {
    try {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

}