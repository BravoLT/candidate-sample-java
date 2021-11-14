package com.bravo.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bravo.user.App;
import com.bravo.user.MapperArgConverter;
import com.bravo.user.config.AppConfig;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.service.UserService;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest {

	private static final String TEST_EMAIL = "john.baker@yahoo.com";
	private static final char[] TEST_PASSWORD = { 'B', 'r', 'a', 'v', 'o', '.', 'J', 'o', 'h', 'n', '.', 'B', 'a', 'k',
			'e', 'r' };

	@Autowired
	private AppConfig appConfig;

  @Autowired
  private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@BeforeAll
	public void init() {
		userService.create(buildTestUserSaveDto());
	}

	/**
	 * Builds a common UserSaveDto for testing.
	 * 
	 * @return {@link UserSaveDto}
	 */
	private UserSaveDto buildTestUserSaveDto() {
		UserSaveDto userSaveDto = new UserSaveDto();
		userSaveDto.setFirstName("John");
		userSaveDto.setLastName("Baker");
		userSaveDto.setEmail(TEST_EMAIL);
		userSaveDto.setPassword(TEST_PASSWORD.clone());
		userSaveDto.setPhoneNumber("5555555555");
		return userSaveDto;
	}

	@ParameterizedTest
	@CsvFileSource(resources = ("/createUserTests.csv"), delimiter = '$', lineSeparator = ">")
	void createUser(@ConvertWith(MapperArgConverter.class) UserSaveDto userSaveDto, boolean isValid) throws Exception {
		String json = appConfig.objectMapperBuilder().build().writeValueAsString(userSaveDto);
		if (isValid) {
			this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(json))
					.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("John Doe"));
		} else {
			this.mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(json))
					.andExpect(status().isBadRequest());
		}
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

	@ParameterizedTest
	@CsvFileSource(resources = ("/authenticateUserTests.csv"), delimiter = '$', lineSeparator = ">")
	void authenticateUser(@ConvertWith(MapperArgConverter.class) UserSaveDto userSaveDto, boolean isValid)
			throws Exception {
		String json = appConfig.objectMapperBuilder().build().writeValueAsString(userSaveDto);
		if (isValid) {
			this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content(json))
					.andExpect(status().isOk()).andExpect(jsonPath("$.user.name").value("John Baker"));
		} else {
			this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content(json))
					.andExpect(status().isUnauthorized());
		}
	}

	@Test
	void authenticateMissingRequestBody() throws Exception {
		this.mockMvc.perform(post("/user/authenticate").contentType(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest());
	}

}