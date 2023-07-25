package com.bravo.user.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bravo.user.App;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;

@ContextConfiguration(classes = { App.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@DirtiesContext
@AutoConfigureMockMvc
class AddressControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressService addressService;

	private List<AddressDto> addresses;

	@BeforeEach
	public void beforeEach() {
		final List<Integer> ids = IntStream.range(1, 10).boxed().toList();

		this.addresses = ids.stream().map(id -> createAddressDto(Integer.toString(id)))
				.collect(Collectors.toList());
	}

	@Test
	void getRetrieveByUserId() throws Exception {
		final String userId = "123a-456b";

		when(addressService.retrieveByUserId(anyString())).thenReturn(addresses);

		final ResultActions result = this.mockMvc.perform(get("/address/retrieve/".concat(userId)))
				.andExpect(status().isOk());

		for (int i = 0; i < addresses.size(); i++) {
			result.andExpect(jsonPath(String.format("$[%d].id", i)).value(addresses.get(i).getId()));
		}

		verify(addressService).retrieveByUserId(userId);
	}

	@Test
	void getRetrieveByUserId_Space() throws Exception {
		this.mockMvc.perform(get("/address/retrieve/ /")).andExpect(status().isNotFound());
	}

	@Test
	void getRetrieveByUserId_Missing() throws Exception {
		this.mockMvc.perform(get("/address/retrieve")).andExpect(status().isNotFound());
	}

	private AddressDto createAddressDto(final String id) {
		final AddressDto address = new AddressDto();
		address.setId(id);
		return address;
	}

}
