package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private ResourceMapper resourceMapper;

    @MockBean
    private PaymentRepository paymentRepository;

    private List<PaymentDto> dtoPayments;

    @BeforeEach
    void setUp() {
        final List<Integer> ids = IntStream
                .range(1, 10)
                .boxed()
                .collect(Collectors.toList());

        final List<Payment> daoPayments = ids.stream()
                .map(id -> createPayment((Integer.toString(id))))
                .collect(Collectors.toList());

        this.dtoPayments = ids.stream()
                .map(id -> createPaymentDto(Integer.toString(id)))
                .collect(Collectors.toList());

        when(paymentRepository.findByUserId(anyString())).thenReturn(daoPayments);
        when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
    }

    @Test
    void retrieveByUserId() {
        final String userId = UUID.randomUUID().toString();
        final List<PaymentDto> result = paymentService.retrieveByUserId(userId);

        assertEquals(this.dtoPayments, result);

        verify(paymentRepository, times(1)).findByUserId(userId);
    }


    private PaymentDto createPaymentDto(String id) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(id);
        return paymentDto;
    }


    private Payment createPayment(String id) {
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

}
