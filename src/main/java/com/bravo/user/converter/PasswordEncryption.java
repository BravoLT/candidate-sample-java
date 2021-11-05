package com.bravo.user.converter;

import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncryption {
  private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncryption.class);

  private Cipher encryptionCipher;
  private Cipher decriptionCipher;
  
  @Autowired
  public PasswordEncryption(@Qualifier("encryptionCipher") Cipher encryptionCipher,
      @Qualifier("decriptionCipher") Cipher decriptionCipher) {
    this.encryptionCipher = encryptionCipher;
    this.decriptionCipher = decriptionCipher;
  }

  public String encrypt(String rawPassword) {
    try {
      return new String(Base64.getEncoder().encode(encryptionCipher.doFinal(rawPassword.getBytes())));
    } catch (Exception e) {
      LOGGER.error("Failed to encrypy DB Password");
      throw new RuntimeException(e);
    }
  }

  public boolean matches(String rawPassword, String encryptedPassword) {
    try {
      String decodedPassword = new String(decriptionCipher.doFinal(Base64.getDecoder().decode(encryptedPassword)));
      return StringUtils.equals(rawPassword, decodedPassword);
    } catch (Exception e) {
      LOGGER.error("Failed to Decrypt DB Password");
      throw new RuntimeException(e);
    }
  }

}
