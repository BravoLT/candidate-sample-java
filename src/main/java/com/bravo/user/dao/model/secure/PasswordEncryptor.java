package com.bravo.user.dao.model.secure;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor implements AttributeConverter<String, String> {

  @Value("${application.h2-column-encryption.algorithm}")
  private String encryptionAlgorithm;

  @Value("${application.h2-column-encryption.secret}")
  private String secret;

  private Key key;

  @PostConstruct
  public void setup() {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      this.key = new SecretKeySpec(digest.digest(secret.getBytes(StandardCharsets.UTF_8)), this.encryptionAlgorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {
    try {
      Cipher cipher = Cipher.getInstance(this.encryptionAlgorithm);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8)));
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String convertToEntityAttribute(String dbField) {
    try {
      Cipher cipher = Cipher.getInstance(this.encryptionAlgorithm);
      cipher.init(Cipher.DECRYPT_MODE, key);
      return new String(cipher.doFinal(Base64.getDecoder().decode(dbField)));
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new IllegalStateException(e);
    }
  }

}
