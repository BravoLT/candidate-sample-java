package com.bravo.user.service;

import com.bravo.user.config.AppConfig;
import com.bravo.user.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {
        AppConfig.class, PasswordService.class
})
@TestPropertySource(properties = {
        "security.algorithm=AES", "security.secretKey=0123456789012345"
})
public class PasswordServiceTest {

    @Autowired
    private PasswordService service;

    @Test
    public void encryptSmokeTest() {
        String password = "password123";
        String encrypted = service.encrypt(password);
        assertNotNull(encrypted);
    }

    @Test
    public void valid() {
        String password = "password123";
        String expected = "VZa5Zk7vX9jH77eI9pw4zA==";
        String actual = service.encrypt(password);
        assertEquals(actual, expected);
    }
}
