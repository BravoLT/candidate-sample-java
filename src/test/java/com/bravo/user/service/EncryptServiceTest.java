package com.bravo.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EncryptServiceTest {

  private EncryptService encryptService;

  @BeforeEach
  void beforeEach(){
    this.encryptService = null;
  }

  @Test
  void encrypt(){
    setEncryptService();
    final String encrypted = this.encryptService.encrypt("password");
    Assertions.assertEquals("doSiQvzG31cjkMBu+QcN1w==", encrypted);
  }

  @Test
  void encryptWithDifferentSecret(){
    setEncryptService("$DifferentSecret9");
    final String encrypted = this.encryptService.encrypt("password");
    Assertions.assertEquals("gGuJA4Ihv0g7tP1Ej9gjJQ==", encrypted);
  }

  @Test
  void encryptWithDifferentValue(){
    setEncryptService();
    final String encrypted = this.encryptService.encrypt("DifferentValue");
    Assertions.assertEquals("UtX2kBisxCzKXItumXzABQ==", encrypted);
  }

  @Test
  void encryptInvalidInput(){
    setEncryptService();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> this.encryptService.encrypt(null)
    );
  }

  private void setEncryptService(){
    setEncryptService("secret123");
  }

  private void setEncryptService(final String secretKey){
    this.encryptService = new EncryptService(secretKey);
  }
}
