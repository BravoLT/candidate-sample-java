package com.bravo.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bravo.user.App;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

	@Test
	void createSuccess() throws Exception {
		this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON)
				.content(
						"{\"email\": \"John.Smith@yahoo.com\", \"firstName\": \"John\", \"lastName\": \"Smith\", \"password\": \"Bravo.John.Smith\", \"phoneNumber\": \"5555555555\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("John Smith"));
	}

	@Test
	void createInvalidEmail() throws Exception {
		this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(
				"{\"email\": \"John.Smith\", \"firstName\": \"John\", \"lastName\": \"Smith\", \"password\": \"Bravo.John.Smith\", \"phoneNumber\": \"5555555555\"}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createInvalidPassword() throws Exception {
		this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(
				"{\"email\": \"John.Smith@yahoo.com\", \"firstName\": \"John\", \"lastName\": \"Smith\", \"password\": \"1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrs\", \"phoneNumber\": \"5555555555\"}"))
				.andExpect(status().isBadRequest());
	}

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
	void authenticateSuccess() throws Exception {
		this.mockMvc
				.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"joyce.roberts@yahoo.com\",\"password\":\"Bravo.Joyce.Roberts\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.user.name").value("Joyce Lucas Roberts"));
	}

	@Test
	void authenticateEmpty() throws Exception {
		this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticateInvalidEmailFormat() throws Exception {
		this.mockMvc
				.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"joyce.roberts\",\"password\":\"12345678\"}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticateInvalidEmailTooLong() throws Exception {
		this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content(
				"{\"email\":\"1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrs@yahoo.com\",\"password\":\"12345678\"}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticateInvalidPasswordTooShort() throws Exception {
		this.mockMvc
				.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"joyce.roberts@yahoo.com\",\"password\":\"1234567\"}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticateInvalidPasswordTooLong() throws Exception {
		this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content(
				"{\"email\":\"joyce.roberts@yahoo.com\",\"password\":\"1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrs\"}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticateMissingRequestBody() throws Exception {
		this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest());
	}

}