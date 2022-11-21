package com.bravo.payment.service;

import com.bravo.App;
import com.bravo.common.dao.model.mapper.ResourceMapper;
import com.bravo.common.utility.PageUtil;
import com.bravo.payment.dao.model.Payment;
import com.bravo.payment.dao.repository.PaymentRepository;
import com.bravo.payment.model.dto.PaymentReadDto;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;
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

  final String userId = randomUUID().toString();
  private List<PaymentReadDto> dtoPayments;

  @BeforeEach
  public void beforeEach() {
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    final Page<Payment> mockPage = mock(Page.class);
    when(paymentRepository.findByUserId(eq(userId), any(PageRequest.class)))
        .thenReturn(mockPage);

    final List<Payment> daoPayments = ids.stream()
        .map(id -> createPayment(Integer.toString(id)))
        .collect(Collectors.toList());
    when(mockPage.getContent()).thenReturn(daoPayments);
    when(mockPage.getTotalPages()).thenReturn(9);

    this.dtoPayments = ids.stream()
        .map(id -> createPaymentReadDto(Integer.toString(id)))
        .collect(Collectors.toList());
    when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
  }

  @Test
  public void retrieveByUserId() {
    final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
    final List<PaymentReadDto> results = paymentService.retrieveByUserId(userId, pageRequest, httpResponse);
    assertEquals(dtoPayments, results);
    assertEquals("9", httpResponse.getHeader("page-count"));
    assertEquals("1", httpResponse.getHeader("page-number"));
    assertEquals("20", httpResponse.getHeader("page-size"));

    verify(paymentRepository).findByUserId(userId, pageRequest);
  }

  @Test
  public void retrieveByUserIdPaged() {
    final PageRequest pageRequest = PageUtil.createPageRequest(2, 5);
    final List<PaymentReadDto> results = paymentService.retrieveByUserId(userId, pageRequest, httpResponse);
    assertEquals(dtoPayments, results);
    assertEquals("9", httpResponse.getHeader("page-count"));
    assertEquals("2", httpResponse.getHeader("page-number"));
    assertEquals("5", httpResponse.getHeader("page-size"));

    verify(paymentRepository).findByUserId(userId, pageRequest);
  }

  private Payment createPayment(final String id) {
    final Payment payment = new Payment();
    payment.setId(id);
    return payment;
  }

  private PaymentReadDto createPaymentReadDto(final String id) {
    final PaymentReadDto payment = new PaymentReadDto();
    payment.setId(id);
    return payment;
  }
}