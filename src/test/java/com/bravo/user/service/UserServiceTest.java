package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.encrypter.PasswordEncryptor;
import com.bravo.user.model.dto.PasswordDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.utility.PageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
    "server.port=9090",
})class UserServiceTest {
  @Autowired
  UserService userService;

  @Autowired
  PasswordEncryptor passwordEncryptor;

  @Test
  void retrieveByNameExact() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("lucas", PageUtil.createPageRequest(null, null), null);
    assertEquals(1, userReadDtos.size());

    userReadDtos = userService.retrieveByName("Lucas", PageUtil.createPageRequest(null, null), null);
    assertEquals(1, userReadDtos.size());
  }

  @Test
  void retrieveByNameExactPaged() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("lucas", PageUtil.createPageRequest(1, 1), null);
    assertEquals("Joyce", userReadDtos.get(0).getFirstName());

    userReadDtos = userService.retrieveByName("Lucas", PageUtil.createPageRequest(2, 1), null);
    assertEquals(0, userReadDtos.size());
  }


  @Test
  void retrieveByNameLike() {
    List<UserReadDto> userReadDtos = userService.retrieveByName("luc%", PageUtil.createPageRequest(null, null), null);
    assertEquals(2, userReadDtos.size());

    userReadDtos = userService.retrieveByName("luc*", PageUtil.createPageRequest(null, null), null);
    assertEquals(2, userReadDtos.size());
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

  @Test
  void testValidatePassword() {
    PasswordDto passwordDto = new PasswordDto();
    String decryptedPassword = passwordEncryptor.decryptPassword("A45jIKG7qo/bVfJZI54J6A==");
    passwordDto.setPassword(decryptedPassword);
    passwordDto.setEmail("Joyce.Roberts@gmail.com");
    UserReadDto readDto = userService.validatePassword(passwordDto);
    assertEquals(readDto.getFirstName() , "Joyce");
    assertEquals(readDto.getLastName() , "Roberts");
  }


  @Test
  void testValidateInvalidPassword() {
    PasswordDto passwordDto = new PasswordDto();
    passwordDto.setPassword("invalidPassword");
    passwordDto.setEmail("Joyce.Roberts@gmail.com");
    try {
      UserReadDto readDto = userService.validatePassword(passwordDto);
    } catch(Exception e) {
      assertEquals(e.getMessage(), "DataNotFoundException | Invalid user name or password");
    }
  }

  @Test
  void testValidateInvalidPasswordWithUserDoesNotExists() {
    PasswordDto passwordDto = new PasswordDto();
    passwordDto.setPassword("invalidPassword");
    passwordDto.setEmail("invalid.users@gmail.com");
    try {
      UserReadDto readDto = userService.validatePassword(passwordDto);
    } catch(Exception e) {
      assertEquals(e.getMessage(), "DataNotFoundException | Invalid user name or password");
    }
  }
}