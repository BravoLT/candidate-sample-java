package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.exception.InvalidCredentialsException;
import com.bravo.user.model.dto.UserAuthDto;
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

  @Test
  void authenticateNonExistentEmail() {
    UserAuthDto request = new UserAuthDto();
    request.setEmail("not-an-email");
    request.setPassword("password");
    assertThrows(
            InvalidCredentialsException.class,
            () -> userService.authenticate(request)
    );
  }

  @Test
  void authenticateBadPassword() {
    UserAuthDto request = new UserAuthDto();
    request.setEmail("01e24e4e-1018-40fa-b92a-a7ad669e7805@gmail.com");
    request.setPassword("password");
    assertThrows(
            InvalidCredentialsException.class,
            () -> userService.authenticate(request)
    );
  }

  @Test
  void authenticateHappyPath() {
    UserAuthDto request = new UserAuthDto();
    request.setEmail("01e24e4e-1018-40fa-b92a-a7ad669e7805@gmail.com");
    request.setPassword("Chelsea");
    userService.authenticate(request);
  }
}