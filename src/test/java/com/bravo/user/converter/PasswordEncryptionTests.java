package com.bravo.user.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bravo.user.App;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@TestPropertySource(properties = {
    "security.encryption.type=AES",
    "security.encryption.key=MySup3rS3cr3tK3y"
})
public class PasswordEncryptionTests {

  @Autowired
  private PasswordEncryption passwordEncryption;
  
  
  @Test
  void encryptedPasswordIsDifferent() {
    String before = "TestPassword";
    String after = passwordEncryption.encrypt(before);
    Assertions.assertNotEquals(before, after);
  }
  
  @Test
  void encryptedThenDecryptedPasswordMatches() {
    String before = "TestPassword";
    String after = passwordEncryption.encrypt(before);
    Assertions.assertTrue(passwordEncryption.matches(before, after));
  }
  
  @Test
  void encryptPasswordRuneTimeExceptionThrown() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      passwordEncryption.encrypt(null);
    });
  }
  
  @Test
  void matchesRuneTimeExceptionThrown() {
    Assertions.assertThrows(RuntimeException.class, () -> {
     passwordEncryption.matches(null, null);
    });
  }
  
}
