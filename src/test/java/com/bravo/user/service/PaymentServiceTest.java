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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
	private PaymentService paymentService;

    @MockBean
	private ResourceMapper resourceMapper;

	@MockBean
	private PaymentRepository  paymentRepository;

	private List<PaymentDto> dtoPayments;

    @BeforeEach
	public void beforeEach() {
        log.info("Initializing  payment list");
		final List<Integer> userIds = IntStream.range(1, 10).boxed().toList();

		final List<Payment> daoPayments = userIds.stream()
				.map(id -> createPayment(Integer.toString(id))).collect(Collectors.toList());

        when(paymentRepository.findByUserId(anyString())).thenReturn(daoPayments);

        this.dtoPayments = userIds.stream().map(id -> createPaymentDto(Integer.toString(id)))
				.collect(Collectors.toList());

		when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
	}

    @Test
	void getRetrieveByUserId() throws Exception {

        log.info("ENTRY::: PaymentServiceTest.getRetrieveByUserId");
		final String userId = "00963d9b-f884-485e-9455-fcf30c6ac379";
        
		final List<PaymentDto> results = paymentService.retrieveByUserId(userId);
        log.info("By do the Assert Equals");
		assertEquals(dtoPayments, results);
        log.info("By Verify");
        verify(paymentRepository).findByUserId(userId);
	}

    private Payment createPayment(final String id) {
		final Payment payment = new Payment();
        payment.setId(id);
		payment.setCardNumber("1111");
        payment.setExpiryMonth(00);
        payment.setExpiryYear(11); 
		return payment;
	}

    private PaymentDto createPaymentDto(String userId) {
        final PaymentDto payment = new PaymentDto();
        payment.setId(userId);
        payment.setCardNumberLast4("1111");
        payment.setExpiryMonth(00);
        payment.setExpiryYear(11);
        return payment;
    }

}
