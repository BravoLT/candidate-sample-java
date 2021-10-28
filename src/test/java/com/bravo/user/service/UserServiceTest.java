/* (C)2021 */
package com.bravo.user.service;

import static com.bravo.user.constants.Constants.ErrorMessages.INCORRECT_USER_EMAIL_OR_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

import com.bravo.user.App;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.utility.PageUtil;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
        properties = {
            "server.port=9090",
        })
class UserServiceTest {
    @Autowired UserService userService;

    @Test
    void retrieveByNameExact() {
        List<UserReadDto> userReadDtos =
                userService.retrieveByName("lucas", PageUtil.createPageRequest(null, null), null);
        assertEquals(2, userReadDtos.size());

        userReadDtos =
                userService.retrieveByName("Lucas", PageUtil.createPageRequest(null, null), null);
        assertEquals(2, userReadDtos.size());
    }

    @Test
    void retrieveByNameExactPaged() {
        List<UserReadDto> userReadDtos =
                userService.retrieveByName("lucas", PageUtil.createPageRequest(1, 1), null);
        assertEquals("Joyce", userReadDtos.get(0).getFirstName());

        userReadDtos = userService.retrieveByName("Lucas", PageUtil.createPageRequest(2, 1), null);
        assertEquals("Lucas", userReadDtos.get(0).getFirstName());
    }

    @Test
    void retrieveByNameLike() {
        List<UserReadDto> userReadDtos =
                userService.retrieveByName("luc%", PageUtil.createPageRequest(null, null), null);
        assertEquals(5, userReadDtos.size());

        userReadDtos =
                userService.retrieveByName("luc*", PageUtil.createPageRequest(null, null), null);
        assertEquals(5, userReadDtos.size());
    }

    @Test
    void retrieveByNameNot() {
        List<UserReadDto> userReadDtos =
                userService.retrieveByName("!s", PageUtil.createPageRequest(null, null), null);
        assertEquals(20, userReadDtos.size());
    }

    @Test
    void retrieveNameNoResults() {
        List<UserReadDto> userReadDtos =
                userService.retrieveByName("zxy", PageUtil.createPageRequest(null, null), null);
        assertEquals(0, userReadDtos.size());
    }

    @Test
    void retrieveNameOnlyControl() {
        List<UserReadDto> userReadDtos =
                userService.retrieveByName("!", PageUtil.createPageRequest(null, null), null);
        assertEquals(20, userReadDtos.size());

        userReadDtos =
                userService.retrieveByName("%", PageUtil.createPageRequest(null, null), null);
        assertEquals(20, userReadDtos.size());

        userReadDtos =
                userService.retrieveByName("*", PageUtil.createPageRequest(null, null), null);
        assertEquals(20, userReadDtos.size());
    }

    @Test
    void retrieveByEmailOrPasswordExactMatch() {
        UserReadDto userReadDto =
                userService.retrieveByEmailAndPassword("Elisabeth.Nudds@gmail.com", "qabu");
        assertNotNull(userReadDto);
    }

    @Test
    void retrieveByEmailOrPasswordUserNotFound() {
        DataNotFoundException exception =
                Assertions.assertThrows(
                        DataNotFoundException.class,
                        () -> {
                            userService.retrieveByEmailAndPassword(
                                    "Elisabeth.NudDs@gmail.com", "qabu");
                        });
        assertEquals(
                "DataNotFoundException | " + INCORRECT_USER_EMAIL_OR_PASSWORD,
                exception.getMessage());
    }

    @Test
    void retrieveByEmailOrPasswordPasswordIncorrect() {
        DataNotFoundException exception =
                Assertions.assertThrows(
                        DataNotFoundException.class,
                        () -> {
                            userService.retrieveByEmailAndPassword(
                                    "Elisabeth.Nudds@gmail.com", "cabu");
                        });
        assertEquals(
                "DataNotFoundException | " + INCORRECT_USER_EMAIL_OR_PASSWORD,
                exception.getMessage());
    }

    @Test
    void retrieveByEmailOrPasswordPasswordEmpty() {
        DataNotFoundException exception =
                Assertions.assertThrows(
                        DataNotFoundException.class,
                        () -> {
                            userService.retrieveByEmailAndPassword("Elisabeth.Nudds@gmail.com", "");
                        });
        assertEquals("DataNotFoundException | Password cannot be empty.", exception.getMessage());
    }

    @Test
    void retrieveByEmailOrPasswordNull() {
        DataNotFoundException exception =
                Assertions.assertThrows(
                        DataNotFoundException.class,
                        () -> {
                            userService.retrieveByEmailAndPassword(
                                    "Elisabeth.Nudds@gmail.com", null);
                        });
        assertEquals("DataNotFoundException | Password cannot be empty.", exception.getMessage());
    }

    @Test
    void retrieveByEmail_EmailEmpty() {
        DataNotFoundException exception =
                Assertions.assertThrows(
                        DataNotFoundException.class,
                        () -> {
                            userService.retrieveByEmailAndPassword("", "qabu");
                        });
        assertEquals(
                "DataNotFoundException | " + INCORRECT_USER_EMAIL_OR_PASSWORD,
                exception.getMessage());
    }
}
