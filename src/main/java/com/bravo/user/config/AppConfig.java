package com.bravo.user.config;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.bravo.user.utility.DateUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class AppConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

  @Value("${server.port:8080}")
  private int serverPort;

  @Value("${security.encryption.type}")
  private String encryptionType;

  @Value("${security.encryption.key}")
  private String encryptionKey;

  @Bean
  public int serverPort() {
    return serverPort;
  }
  
  @Bean
  public String encryptionType() {
    return encryptionType;
  }
  
  @Bean
  public String encryptionKey() {
    return encryptionKey;
  }

  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder() {
    return new Jackson2ObjectMapperBuilder()
        .createXmlMapper(false)
        .indentOutput(true)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .serializers(
            new LocalDateSerializer(DateUtil.DATE_FORMAT),
            new LocalDateTimeSerializer(DateUtil.DATE_TIME_FORMAT),
            new LocalTimeSerializer(DateUtil.TIME_FORMAT))
        .deserializers(
            new LocalDateDeserializer(DateUtil.DATE_FORMAT),
            new LocalDateTimeDeserializer(DateUtil.DATE_TIME_FORMAT),
            new LocalTimeDeserializer(DateUtil.TIME_FORMAT))
        .modules(
            new JavaTimeModule(),
            new ParameterNamesModule(),
            new Jdk8Module());
  }

  @Bean
  public MapperFacade mapperFacade(){
    return new DefaultMapperFactory.Builder().build().getMapperFacade();
  }

  /**
   * I believe these two methods existing here provide a few benefits. <br>
   * 1. Not having to bring the AppConfig file into the password encryption file
   * 2. Would allow us to easily rework the Password Encryption Implementation be
   * reused if we need to pass in different Cipher implementations <br>
   * 3. Avoids creating the same objects by executing the same lines every time we
   * have a user that needs verification
   */

  @Bean(name = "encryptionCipher")
  public Cipher encryptCipher() {
    Key key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
    try {
      Cipher cipher = Cipher.getInstance(encryptionType);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher;
    } catch (Exception e) {
      LOGGER.error("Failed to initilize Cipher based on Key and Type values in YAML");
      throw new RuntimeException(e);
    }
  }

  @Bean(name = "decriptionCipher")
  public Cipher decriptCipher() {
    Key key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
    try {
      Cipher cipher = Cipher.getInstance(encryptionType);
      cipher.init(Cipher.DECRYPT_MODE, key);
      return cipher;
    } catch (Exception e) {
      LOGGER.error("Failed to initilize Cipher based on Key and Type values in YAML");
      throw new RuntimeException(e);
    }
  }
}
