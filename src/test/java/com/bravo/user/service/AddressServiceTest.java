package com.bravo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bravo.user.App;
import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.model.dto.AddressDto;

@ContextConfiguration(classes = { App.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AddressServiceTest {
	//I looked at tests that have already been written to try to get a better understanding
	//of the different components of this app
	//autowired ? - allows you to inject the object dependency implicitly
	@Autowired
	private AddressService addressService;
	//mock bean? this will replace any existing bean of the same type in the application context
	//according to what I have read, you can also use a @Qualifier notation and pass in qualifier metadata
	//mock resource mapper to use in tests
	@MockBean
	private ResourceMapper resourceMapper;

	//mock address repo to use in tests
	@MockBean
	private AddressRepository addressRepository;
	//empty (?) list of data transfer object - addresses
	private List<AddressDto> dtoAddresses;

	@BeforeEach
	public void beforeEach() {
		/* wow! this is my first time learning about the Collectors class - pretty cool!
			-allows us to accumulate/reduce values into a collection
		IntStream.range()? - sequence of primitive int values that fall between given range, .boxed() - each element boxed to an Integer

		*/
		final List<Integer> ids = IntStream.range(1, 10).boxed().collect(Collectors.toList());
		//here we use stream() to save a list of addresses
		final List<Address> daoAddresses = ids.stream()
				.map(id -> createAddress(Integer.toString(id))).collect(Collectors.toList());
		//I think what is happening now is the id's are being mapped to the daoAddresses list
		//when keyword - Mockito class! also new to me - sent down a new rabbit hole
		//Mockito is a mocking framework for unit tests in Java - allows the creation of test double objects in automated unit tests
			//"used to mock interfaces so that a dummy functionality can be added to a mock interface that can be used in testing"
		when(addressRepository.findByUserId(anyString())).thenReturn(daoAddresses);
		//this keyword refers to this specific instance
		this.dtoAddresses = ids.stream().map(id -> createAddressDto(Integer.toString(id)))
				.collect(Collectors.toList());

		when(resourceMapper.convertAddresses(daoAddresses)).thenReturn(dtoAddresses);
	}
//testing that endpoint retrieves correct id value
	@Test
	void retrieveByUserId() {
		final String userId = "123a-456b";
		final List<AddressDto> results = addressService.retrieveByUserId(userId);
		assertEquals(dtoAddresses, results);

		verify(addressRepository).findByUserId(userId);
	}

	private Address createAddress(final String id) {
		final Address address = new Address();
		address.setId(id);
		return address;
	}

	private AddressDto createAddressDto(final String id) {
		final AddressDto address = new AddressDto();
		address.setId(id);
		return address;
	}

}
