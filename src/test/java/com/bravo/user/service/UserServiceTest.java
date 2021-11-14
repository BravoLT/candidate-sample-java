package com.bravo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bravo.user.App;
import com.bravo.user.exception.UnauthorizedException;
import com.bravo.user.model.dto.UserAuthDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.PageUtil;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
    "server.port=9090",
})
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest {

	private static final String TEST_EMAIL = "john.smith@yahoo.com";
	private static final char[] TEST_PASSWORD = { 'B', 'r', 'a', 'v', 'o', '.', 'J', 'o', 'h', 'n', '.', 'S', 'm', 'i',
			't', 'h' };

  @Autowired
  UserService userService;

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
		userSaveDto.setLastName("Smith");
		userSaveDto.setEmail(TEST_EMAIL);
		userSaveDto.setPassword(TEST_PASSWORD.clone());
		userSaveDto.setPhoneNumber("5555555555");
		return userSaveDto;
	}

  @Test
  void retrieveByNameExact() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("lucas", PageUtil.createPageRequest(null, null), null);
    assertEquals(2, userReadDtos.size());

    userReadDtos = userService.retrieveByName("Lucas", PageUtil.createPageRequest(null, null), null);
    assertEquals(2, userReadDtos.size());
  }

  @Test
  void retrieveByNameExactPaged() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("lucas", PageUtil.createPageRequest(1, 1), null);
    assertEquals("Joyce", userReadDtos.get(0).getFirstName());

    userReadDtos = userService.retrieveByName("Lucas", PageUtil.createPageRequest(2, 1), null);
    assertEquals("Lucas", userReadDtos.get(0).getFirstName());
  }


  @Test
  void retrieveByNameLike() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("luc%", PageUtil.createPageRequest(null, null), null);
    assertEquals(5, userReadDtos.size());

    userReadDtos = userService.retrieveByName("luc*", PageUtil.createPageRequest(null, null), null);
    assertEquals(5, userReadDtos.size());
  }

  @Test
  void retrieveByNameNot() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("!s", PageUtil.createPageRequest(null, null), null);
    assertEquals(20, userReadDtos.size());
  }

  @Test
  void retrieveNameNoResults() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("zxy", PageUtil.createPageRequest(null, null), null);
    assertEquals(0, userReadDtos.size());
  }


  @Test
  void retrieveNameOnlyControl() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("!", PageUtil.createPageRequest(null, null), null);
    assertEquals(20, userReadDtos.size());

    userReadDtos = userService.retrieveByName("%", PageUtil.createPageRequest(null, null), null);
    assertEquals(20, userReadDtos.size());

    userReadDtos = userService.retrieveByName("*", PageUtil.createPageRequest(null, null), null);
    assertEquals(20, userReadDtos.size());

  }

	/**
	 * Verify that this service method, given a valid user and the correct password,
	 * does not throw a BadRequestException.
	 */
	@Test
	void authenticateUserSuccess() {
		UserAuthDto userAuthDto = new UserAuthDto();
		userAuthDto.setEmail(TEST_EMAIL);
		userAuthDto.setPassword(TEST_PASSWORD.clone());
		userService.authenticateUser(userAuthDto);
	}

	/**
	 * Verify that an invalid user and a wrong password (separately) will throw a
	 * BadRequestException. Then make sure both exceptions caught have the same
	 * message.
	 */
	@Test
	void authenticateUserInvalidUserAndWrongPassword() {
		UserAuthDto userAuthDtoInvalidUser = new UserAuthDto();
		userAuthDtoInvalidUser.setEmail("john.smit@yahoo.com");
		userAuthDtoInvalidUser.setPassword(TEST_PASSWORD.clone());
		UnauthorizedException unauthorizedExceptionOne = assertThrows(UnauthorizedException.class, () -> {
			userService.authenticateUser(userAuthDtoInvalidUser);
		});

		UserAuthDto userAuthDtoWrongPassword = new UserAuthDto();
		userAuthDtoWrongPassword.setEmail(TEST_EMAIL);
		userAuthDtoWrongPassword.setPassword((new String("Bravo").toCharArray()));
		UnauthorizedException unauthorizedExceptionTwo = assertThrows(UnauthorizedException.class, () -> {
			userService.authenticateUser(userAuthDtoInvalidUser);
		});

		assertEquals(unauthorizedExceptionOne.getMessage(), unauthorizedExceptionTwo.getMessage());
	}

}