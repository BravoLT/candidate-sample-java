package com.bravo.user.dao.model.mapper;

import com.bravo.user.MapperArgConverter;
import com.bravo.user.dao.model.Profile;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.ProfileReadDto;
import com.bravo.user.model.dto.UserReadDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import ma.glasnost.orika.impl.DefaultMapperFactory;

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

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/convertProfileTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void convertProfileTest(
      @ConvertWith(MapperArgConverter.class) Profile profile,
      @ConvertWith(MapperArgConverter.class) ProfileReadDto profileReadDto) {
    Assertions.assertEquals(profileReadDto, new ResourceMapper(
        new DefaultMapperFactory.Builder().build().getMapperFacade()).convertProfile(profile));
  }

  
}