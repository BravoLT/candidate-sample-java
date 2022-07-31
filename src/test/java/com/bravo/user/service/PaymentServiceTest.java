package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.dto.UserReadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
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
  private PaymentService paymentService;

  @MockBean
  private ResourceMapper resourceMapper;

  @MockBean
  private UserService userService;

  @MockBean
  private PaymentRepository paymentRepository;

  private UserReadDto user;
  private List<PaymentDto> dtoPayments;

  @BeforeEach
  public void beforeEach(){

    this.user = createUser();
    final List<Integer> ids = IntStream
            .range(1, 10)
            .boxed()
            .collect(Collectors.toList());

    final List<Payment> daoPayments = ids.stream()
            .map(id -> createPayment(Integer.toString(id), this.user.getId())).collect(Collectors.toList());

    this.dtoPayments = ids.stream().map(id -> createPaymentDto(Integer.toString(id)))
            .collect(Collectors.toList());

    when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);

    when(userService.retrieve(any(String.class)))
            .thenReturn(user);
    when(paymentRepository.findByUserId(any(String.class)))
            .thenReturn(daoPayments);

  }

  @Test
  public void retrieveByUserId() {
    final String input = "1";
    final List<PaymentDto> results = paymentService.retrieve(input);
    System.out.println(results);
	assertEquals(dtoPayments, results);

  }

    private Payment createPayment(final String id, final String userId){
    final Payment payment = new Payment();
    payment.setId(id);
    payment.setUserId(userId);
    return payment;
  }

  private UserReadDto createUser(){
    final UserReadDto user = new UserReadDto();
    user.setId("1");
    return user;
  }

  private PaymentDto createPaymentDto(final String id) {
    final PaymentDto paymentDto = new PaymentDto();
    paymentDto.setId(id);
    return paymentDto;
  }

}