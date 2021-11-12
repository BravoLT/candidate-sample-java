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

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
    "server.port=9090", "security.algorithm=AES", "security.secretKey=SECRET"
})
public class AppConfigTest {

  @Autowired
  private AppConfig appConfig;

  @Autowired
  private Cipher cipher;

  @Autowired
  private Key key;

  @Test
  public void serverPort(){
    final int actual = appConfig.serverPort();
    final int expect = 9090;
    Assertions.assertEquals(expect, actual);
  }

  @Test
  public void cipher() {
    final String actual = cipher.getAlgorithm();
    final String expect = "AES";
    Assertions.assertEquals(expect, actual);
  }

  @Test
  public void key() {
    final String actualAlgo = key.getAlgorithm();
    final String expectAlgo = "AES";
    Assertions.assertEquals(expectAlgo, actualAlgo);

    final String actualKey = new String(key.getEncoded());
    final String expectKey = "SECRET";
    Assertions.assertEquals(expectKey, actualKey);
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
}
