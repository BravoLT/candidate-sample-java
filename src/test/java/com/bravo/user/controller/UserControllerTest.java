package com.bravo.user.controller;

import com.bravo.user.App;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
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

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/createUser.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void createUser(String newUser) throws Exception {
    this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(newUser))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Q Doe"))
        .andExpect(jsonPath("$.email").value("john.q.doe@example.com"))
        .andExpect(jsonPath("$.role").isEmpty())
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/createUserMissingEmail.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void createUserMissingEmail(String newUser) throws Exception {
    this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(newUser))
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/createUserInvalidRole.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void createUserInvalidRole(String newUser) throws Exception {
    this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(newUser))
        .andExpect(jsonPath("$.role").isEmpty());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/createUserAdminRole.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void createUserAdminRole(String newUser) throws Exception {
    this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(newUser))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/createUserDuplicateEmail.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void createUserDuplicateEmail(String newUser) throws Exception {
    this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(newUser))
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/validateUser.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void validateUser(
    String body
  ) throws Exception {
    this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk());
  }

  @Test
  void validateUserMissingBody() throws Exception {
    this.mockMvc.perform(post("/user/validate"))
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/validateUserMalformedBody.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void validateUserMalformedBody(String body) throws Exception {
    this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/validateUserMissingEmail.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void validateUserMissingEmail(String body) throws Exception {
    this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isForbidden());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/validateUserInvalidEmail.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void validateUserInvalidEmail(String body) throws Exception {
    this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isForbidden());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/validateUserMissingPassword.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void validateUserMissingPassword(String body) throws Exception {
    this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isForbidden());
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/validateUserInvalidPassword.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void validateUserInvalidPassword(String body) throws Exception {
    this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isForbidden());
  }

}
