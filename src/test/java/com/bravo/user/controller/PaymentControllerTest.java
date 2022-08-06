package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

  final static String RESOURCE_URL = "/payment/retrieve/";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentService paymentService;

  private List<PaymentDto> payments;

  @BeforeEach
  public void beforeEach(){
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    this.payments = ids.stream()
        .map(id -> createPaymentDto(Integer.toString(id)))
        .collect(Collectors.toList());
  }

  @Test
  void testRetrieveByUserIdSuccess() throws Exception {
    when(paymentService
        .retrieveByUserId(anyString(), any(PageRequest.class), any(HttpServletResponse.class)))
        .thenReturn(payments);

    UUID userId = UUID.randomUUID();

    final ResultActions result = this.mockMvc
        .perform(get(RESOURCE_URL + userId))
        .andExpect(status().isOk());

    for(int i = 0; i < payments.size(); i++){
      result.andExpect(jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId()));
    }

    final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
    verify(paymentService).retrieveByUserId(
        eq(userId.toString()), eq(pageRequest), any(HttpServletResponse.class)
    );
  }

  @Test
  void testRetrieveByUserIdNotFoundFailure() throws Exception {
    this.mockMvc.perform(get(RESOURCE_URL))
        .andExpect(status().isNotFound());
  }

  @ParameterizedTest
  @ValueSource(strings = {"Huh", "What?!", "WHY!", "0x00002342342526"})
  void testRetrieveByUserIdInvalidIdFailures(String invalidId) throws Exception {
    this.mockMvc.perform(get(RESOURCE_URL + invalidId))
        .andExpect(status().isBadRequest());
  }

  private PaymentDto createPaymentDto(final String id){
    PaymentDto dto = new PaymentDto();
    dto.setId(id);
    dto.setCardNumberLast4("5150");
    dto.setExpiryMonth(1);
    dto.setExpiryYear(2024);
    return dto;
  }
}