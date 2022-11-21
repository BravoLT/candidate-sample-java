package com.bravo.payment.controller;

import com.bravo.App;
import com.bravo.common.utility.PageUtil;
import com.bravo.payment.model.dto.PaymentReadDto;
import com.bravo.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class PaymentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentService paymentService;

  private List<PaymentReadDto> payments;

  @BeforeEach
  public void beforeEach() {
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    this.payments = ids.stream()
        .map(id -> createPaymentReadDto(Integer.toString(id)))
        .collect(Collectors.toList());
  }

  @Test
  void getRetrieveWithUserId() throws Exception {
    final String userId = randomUUID().toString();
    when(paymentService
        .retrieveByUserId(eq(userId), any(PageRequest.class), any(HttpServletResponse.class)))
        .thenReturn(payments);

    final ResultActions result = this.mockMvc
        .perform(get("/payment/retrieve/{userId}", userId))
        .andExpect(status().isOk());

    for (int i = 0; i < payments.size(); i++) {
      result.andExpect(jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId()));
    }

    final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
    verify(paymentService).retrieveByUserId(
        eq(userId), eq(pageRequest), any(HttpServletResponse.class)
    );
  }

  @Test
  void getRetrieveWithUserIdMissing() throws Exception {
    this.mockMvc.perform(get("/payment/retrieve/"))
        .andExpect(status().isNotFound());
  }

  @Test
  void getRetrieveWithUserIdInvalid() throws Exception {
    this.mockMvc.perform(get("/payment/retrieve/  "))
        .andExpect(status().isBadRequest());
  }

  private PaymentReadDto createPaymentReadDto(final String id) {
    final PaymentReadDto payment = new PaymentReadDto();
    payment.setId(id);
    return payment;
  }
}