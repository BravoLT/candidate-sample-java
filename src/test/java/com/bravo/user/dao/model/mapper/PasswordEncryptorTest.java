package com.bravo.user.dao.model.mapper;

import com.bravo.user.config.AppConfig;
import com.bravo.user.dao.model.PasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = {
        AppConfig.class, PasswordEncryptor.class
})
@TestPropertySource(properties = {
        "security.algorithm=AES", "security.secretKey=0123456789012345"
})
public class PasswordEncryptorTest {

    @Autowired
    private PasswordEncryptor encryptor;

    @Test
    public void encryptSmokeTest() {
        String password = "password123";
        String encrypted = encryptor.convertToDatabaseColumn(password);
        assertNotNull(encrypted);
    }

    @Test
    public void decryptSmokeTest() {
        String encrypted = "VZa5Zk7vX9jH77eI9pw4zA==";
        String password  = encryptor.convertToEntityAttribute(encrypted);
        assertNotNull(password);
    }

    @Test
    public void encryptThenDecrypt() {
        String password = "supersecurepassword123";
        String encrypted = encryptor.convertToDatabaseColumn(password);
        String decrypted = encryptor.convertToEntityAttribute(encrypted);
        assertEquals(password, decrypted);
    }

    public void a(){

    }
}
