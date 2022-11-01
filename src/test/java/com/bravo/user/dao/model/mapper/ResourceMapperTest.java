package com.bravo.user.dao.model.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bravo.user.App;
import com.bravo.user.MapperArgConverter;
import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.model.dto.PaymentDto;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ResourceMapperTest {

  @Autowired
  private ResourceMapper resourceMapper;

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/ResourceMapperTest/convertUserTest.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
	void convertUserTest(
      @ConvertWith(MapperArgConverter.class) User user,
      @ConvertWith(MapperArgConverter.class) UserReadDto userReadDto
  ) {
    Assertions.assertEquals(userReadDto, resourceMapper.convertUser(user));
  }

	@ParameterizedTest
	@CsvFileSource(
			resources = ("/ResourceMapperTest/convertAddressTest.csv"),
			delimiter = '$',
			lineSeparator = ">")
	void convertAddressTest(
			@ConvertWith(MapperArgConverter.class) Address address,
			@ConvertWith(MapperArgConverter.class) AddressDto addressDto) {
		Assertions.assertEquals(addressDto, resourceMapper.convertAddress(address));
	}

	@ParameterizedTest
	@CsvFileSource(
			resources = ("/ResourceMapperTest/convertPaymentTest.csv"),
			delimiter = '$',
			lineSeparator = ">")
	void convertPaymentTest(
			@ConvertWith(MapperArgConverter.class) Payment payment,
			@ConvertWith(MapperArgConverter.class) PaymentDto paymentDto) {
	  Assertions.assertEquals(paymentDto, resourceMapper.convertPayment(payment));
	}
}
