package com.bravo.user.config;

import com.bravo.user.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFacade;
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
@TestPropertySource(properties = {
    "server.port=9090",
    "security.encryption.type=AES",
    "security.encryption.key=MySup3rS3cr3tK3y"
})
public class AppConfigTest {

  @Autowired
  private AppConfig appConfig;

  @Test
  public void serverPort(){
    final int actual = appConfig.serverPort();
    final int expect = 9090;
    Assertions.assertEquals(expect, actual);
  }

  @Test
  public void objectMapperBuilder(){
    final ObjectMapper objectMapper = appConfig.objectMapperBuilder().build();
    final String actual = objectMapper.getClass().getName();
    final String expect = "com.fasterxml.jackson.databind.ObjectMapper";
    Assertions.assertEquals(expect, actual);
  }

  @Test
  public void mapperFacade(){
    final MapperFacade mapperFacade = appConfig.mapperFacade();
    final String actual = mapperFacade.getClass().getName();
    final String expect = "ma.glasnost.orika.impl.MapperFacadeImpl";
    Assertions.assertEquals(expect, actual);
  }
  
  @Test
  public void encryptionType() {
    final String actual = appConfig.encryptionType();
    final String expect = "AES";
    Assertions.assertEquals(expect, actual);
  }
  
  @Test
  public void encryptionKey() {
    final String actual = appConfig.encryptionKey();
    final String expect = "MySup3rS3cr3tK3y";
    Assertions.assertEquals(expect, actual);
  }
  
  @Test
  public void encryptCipher() {
    final String actual = appConfig.encryptCipher().getClass().getName();
    final String expect = "javax.crypto.Cipher";
    Assertions.assertEquals(expect, actual);
  }
  
  @Test
  public void decryptCipher() {
    final String actual = appConfig.decriptCipher().getClass().getName();
    final String expect = "javax.crypto.Cipher";
    Assertions.assertEquals(expect, actual);
  }
  
}
