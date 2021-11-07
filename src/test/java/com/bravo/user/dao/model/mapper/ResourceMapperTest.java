package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.User;
import com.bravo.user.MapperArgConverter;
import com.bravo.user.model.dto.AddressReadDto;
import com.bravo.user.model.dto.UserReadDto;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

class ResourceMapperTest {

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/convertUserTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  void convertUserTest(
      @ConvertWith(MapperArgConverter.class) User user,
      @ConvertWith(MapperArgConverter.class) UserReadDto userReadDto)
  {
    Assertions.assertEquals(userReadDto, new ResourceMapper(new DefaultMapperFactory.Builder().build().getMapperFacade()).convertUser(user));
  }

  @ParameterizedTest
  @CsvFileSource(
          resources = ("/convertAddressTests.csv"),
          delimiter = '$',
          lineSeparator = ">"
  )
  void convertAddressTest(
          @ConvertWith(MapperArgConverter.class) Address address,
          @ConvertWith(MapperArgConverter.class) AddressReadDto addressReadDto)
  {
    Assertions.assertEquals(addressReadDto, new ResourceMapper(new DefaultMapperFactory.Builder().build().getMapperFacade()).convertAddress(address));
  }

}