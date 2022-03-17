package com.bravo.user.controller;

import com.bravo.user.TestUtils;
import com.bravo.user.exception.PaymentNotFoundException;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

  private static final String SUCCESS_PAYMENT_RESPONSE_PATH = "/response/paymentsResponse.json";
  private static final String PAYMENTS_NOT_FOUND_RESPONSE_PATH = "/response/paymentsNotFoundResponse.json";

  private static final String TEST_USER_ID = "testUserId";
  private static final String PAYMENTS_ENDPOINT = "/payments";
  private static final String USER_ID_PARAM_NAME = "userId";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PaymentService paymentService;

  @Test
  @SneakyThrows
  public void successfullyFoundPaymentByUserIdTest() {
    var successResponse = TestUtils.readFromFile(SUCCESS_PAYMENT_RESPONSE_PATH);
    List<PaymentDto> payments = TestUtils.deserializeFromJson(successResponse, new TypeReference<>() {});

   Mockito.when(paymentService.findPaymentByUserId(TEST_USER_ID)).thenReturn(payments);

    mvc.perform(get(PAYMENTS_ENDPOINT)
            .queryParam(USER_ID_PARAM_NAME, TEST_USER_ID)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(successResponse));
  }

  @Test
  @SneakyThrows
  public void paymentsNotFoundShouldReturnNotFoundWithMessageTest() {
    var paymentsNotFoundResponse = TestUtils.readFromFile(PAYMENTS_NOT_FOUND_RESPONSE_PATH);

    Mockito.when(paymentService.findPaymentByUserId(TEST_USER_ID)).thenThrow(new PaymentNotFoundException(TEST_USER_ID));

    mvc.perform(get(PAYMENTS_ENDPOINT)
            .queryParam(USER_ID_PARAM_NAME, TEST_USER_ID)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().json(paymentsNotFoundResponse));
  }

}