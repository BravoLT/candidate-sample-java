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

import com.bravo.App;
import com.bravo.user.dao.model.Address;
import com.bravo.common.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.model.dto.AddressDto;

@ContextConfiguration(classes = { App.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AddressServiceTest {

	@Autowired
	private AddressService addressService;

	@MockBean
	private ResourceMapper resourceMapper;

	@MockBean
	private AddressRepository addressRepository;

	private List<AddressDto> dtoAddresses;

	@BeforeEach
	public void beforeEach() {
		final List<Integer> ids = IntStream.range(1, 10).boxed().collect(Collectors.toList());

		final List<Address> daoAddresses = ids.stream()
				.map(id -> createAddress(Integer.toString(id))).collect(Collectors.toList());

		when(addressRepository.findByUserId(anyString())).thenReturn(daoAddresses);

		this.dtoAddresses = ids.stream().map(id -> createAddressDto(Integer.toString(id)))
				.collect(Collectors.toList());

		when(resourceMapper.convertAddresses(daoAddresses)).thenReturn(dtoAddresses);
	}

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
