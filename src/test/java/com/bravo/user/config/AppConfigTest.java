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
@TestPropertySource(properties = {
    "server.port=9090",
})
@SpringBootTest
public class AppConfigTest {

  @Autowired
  private AppConfig appConfig;

  @Test
  public void serverPort(){
    final int expect = 9090;
    final int actual = appConfig.serverPort();
    Assertions.assertEquals(expect, actual);
  }

  @Test
  public void objectMapperBuilder(){
    final ObjectMapper objectMapper = appConfig.objectMapperBuilder().build();
    Assertions.assertEquals("object mapper clazz", objectMapper.getClass().getName());
  }

  @Test
  public void mapperFacade(){
    final MapperFacade mapperFacade = appConfig.mapperFacade();
    Assertions.assertEquals("mapper facade clazz", mapperFacade.getClass().getName());
  }
}
