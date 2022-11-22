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
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;


/**
 * Payment Service Test class.
 * 
 * @author Bob Wilson
 * Created November 2022
 * 
 */
@ContextConfiguration(classes = { App.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {

	@Autowired
	private PaymentService pymtService;

	@MockBean
	private ResourceMapper resourceMapper;

	@MockBean
	private PaymentRepository pymtRepository;

	private List<PaymentDto> dtoPayments;
	
	/*
	 * Setup before tests.
	 */
	@BeforeEach
	public void beforeEach() {
		
		// Create some IDs.
		final List<Integer> ids = IntStream
				.range(1, 10)
				.boxed()
				.collect(Collectors.toList());

		final List<Payment> daoPayments = ids
				.stream()
				.map(id -> createPayment(Integer.toString(id)))
				.collect(Collectors.toList());

		when(pymtRepository.findByUserId(anyString())).thenReturn(daoPayments);

		this.dtoPayments = ids
				.stream()
				.map(id -> createPaymentDto(Integer.toString(id)))
				.collect(Collectors.toList());
		
		when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
	}

	/*
	 * Retrieve payments via userId.
	 */
	@Test
	void retrieveByUserId() {
		final String userId = "some userID";
		final List<PaymentDto> results = pymtService.retrieveByUserId(userId);
		assertEquals(dtoPayments, results);

		verify(pymtRepository).findByUserId(userId);
	}

	/*
	 * Create a Payment data object
	 * 
	 * @param id
	 * @return pymt
	 */
	private Payment createPayment(final String id) {
		final Payment pymt = new Payment();
		pymt.setId(id);
		return pymt;
	}

	/*
	 * Create a Payment data transfer object
	 * 
	 * @param id
	 * @return pymtDto
	 */
	private PaymentDto createPaymentDto(final String id) {
		final PaymentDto pymtDto = new PaymentDto();
		pymtDto.setId(id);
		return pymtDto;
	}

}
