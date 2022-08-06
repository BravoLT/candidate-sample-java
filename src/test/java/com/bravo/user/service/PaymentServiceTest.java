package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.utility.PageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {

  @Autowired
  private HttpServletResponse httpResponse;

  @Autowired
  private PaymentService paymentService;

  @MockBean
  private ResourceMapper resourceMapper;

  @MockBean
  private PaymentRepository paymentRepository;

  private List<PaymentDto> paymentDtos;

  @BeforeEach
  public void beforeEach(){
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    final Page<Payment> mockPage = mock(Page.class);
    when(paymentRepository.findPaymentsByUserId(anyString(), any(PageRequest.class)))
        .thenReturn(mockPage);

    final List<Payment> paymentsDao = ids.stream()
        .map(id -> createPayment(Integer.toString(id)))
        .collect(Collectors.toList());
    when(mockPage.getContent()).thenReturn(paymentsDao);
    when(mockPage.getTotalPages()).thenReturn(9);

    this.paymentDtos = ids.stream()
        .map(id -> createPaymentDto(Integer.toString(id)))
        .collect(Collectors.toList());
    when(resourceMapper.convertPayments(paymentsDao)).thenReturn(paymentDtos);
  }

  @Test
  public void retrieveByUserId() {
    final String input = UUID.randomUUID().toString();
    final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
    final List<PaymentDto> results = paymentService.retrieveByUserId(input, pageRequest, httpResponse);
    assertEquals(paymentDtos, results);
	assertEquals("9", httpResponse.getHeader("page-count"));
	assertEquals("1", httpResponse.getHeader("page-number"));
	assertEquals("20", httpResponse.getHeader("page-size"));

    verify(paymentRepository).findPaymentsByUserId(input, pageRequest);
  }

  @Test
  public void retrieveByUserIdPaged() {
    final String input = UUID.randomUUID().toString();
    final PageRequest pageRequest = PageUtil.createPageRequest(2, 5);
    final List<PaymentDto> results = paymentService.retrieveByUserId(input, pageRequest, httpResponse);
    assertEquals(paymentDtos, results);
	assertEquals("9", httpResponse.getHeader("page-count"));
	assertEquals("2", httpResponse.getHeader("page-number"));
	assertEquals("5", httpResponse.getHeader("page-size"));

    verify(paymentRepository).findPaymentsByUserId(input, pageRequest);
  }

  private Payment createPayment(final String id){
    Payment payment = new Payment();
    payment.setId(id);
    payment.setCardNumber("0123456515012112");
    payment.setExpiryMonth(1);
    payment.setExpiryYear(2024);
    payment.setUpdated(LocalDateTime.now());
    return payment;
  }

  private PaymentDto createPaymentDto(final String id){
    PaymentDto dto = new PaymentDto();
    dto.setId(id);
    dto.setCardNumberLast4("5150");
    dto.setExpiryMonth(1);
    dto.setExpiryYear(2024);
    dto.setUpdated(LocalDateTime.now());
    return dto;
  }
}