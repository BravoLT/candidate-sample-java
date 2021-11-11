package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.User;
import com.bravo.user.MapperArgConverter;
import com.bravo.user.model.dto.UserReadDto;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.*;

class ResourceMapperTest {

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/convertUserTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void convertUserTest(
      @ConvertWith(MapperArgConverter.class) User user,
      @ConvertWith(MapperArgConverter.class) UserReadDto userReadDto) {
    Assertions.assertEquals(userReadDto, new ResourceMapper(
        new DefaultMapperFactory.Builder().build().getMapperFacade()).convertUser(user));
  }
}
