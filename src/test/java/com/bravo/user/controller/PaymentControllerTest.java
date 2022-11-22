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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bravo.user.App;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;

/**
 * Payment Controller Test class.
 * 
 * @author Bob Wilson
 * Created November 2022
 * 
 */
@ContextConfiguration(classes = { App.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class PaymentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PaymentService pymtService;
	
	List<PaymentDto> payments;
	
	/*
	 * Setup before all tests.
	 */
	@BeforeEach
	public void beforeEach() {
		final List<Integer> ids = IntStream.range(1, 10).boxed().collect(Collectors.toList());

		this.payments = ids.stream().map(id -> createPaymentDto(Integer.toString(id)))
				.collect(Collectors.toList());
	}
	
	/*
	 * Create a Payment data transfer object.
	 */
	private PaymentDto createPaymentDto(final String id) {
		final PaymentDto payment = new PaymentDto();
		payment.setId(id);
		return payment;
	}

	/*
	 * Tests 'GET' payments by userId.
	 */
	@Test
	void getPaymentsByUserId() throws Exception {
		final String userId = "some_user";

		when(pymtService.retrieveByUserId(anyString())).thenReturn(payments);

		final ResultActions result = this.mockMvc.perform(get("/payment/retrieve/".concat(userId)))
				.andExpect(status().isOk());

		for (int i = 0; i < payments.size(); i++) {
			result.andExpect(jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId()));
		}

		verify(pymtService).retrieveByUserId(userId);
	}

}
