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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest()
@AutoConfigureMockMvc
public class PaymentControllerTest {
    
    @Autowired
	private MockMvc mockMvc;

    @MockBean
	private PaymentService paymentService;

    private List<PaymentDto> payments;

    @BeforeEach
	public void beforeEach() {
        log.info("Initializing  payment list");
		final List<Integer> userIds = IntStream.range(1, 10).boxed().toList();

		this.payments = userIds.stream().map(id -> createPaymentDto(Integer.toString(id)))
				.collect(Collectors.toList());
	}

    @Test
	void getRetrieveByUserId() throws Exception {

        log.info("ENTRY::: PaymentControllerTest.getRetrieveByUserId");
		final String userId = "00963d9b-f884-485e-9455-fcf30c6ac379";
        
		when(paymentService.retrieveByUserId(anyString())).thenReturn(this.payments);

		final ResultActions result = this.mockMvc.perform(get("/payment/retrieve/".concat(userId)))
		 		.andExpect(status().isOk());

		 for (int i = 0; i < payments.size(); i++) {
		 	result.andExpect(jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId()));
		 }
         log.info("Assignment: By  Verify the retrieveByUserId ");
		 verify(paymentService).retrieveByUserId(userId);
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
