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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private ResourceMapper resourceMapper;

    @MockBean
    private PaymentRepository paymentRepository;

    private List<PaymentDto> dtoPayments;

    @BeforeEach
    public void beforeEach() {
        final List<Integer> ids = IntStream.range(1, 10).boxed().collect(Collectors.toList());

        final List<Payment> daoPayments = ids.stream()
                .map(id -> createPayment(Integer.toString(id))).collect(Collectors.toList());

        when(paymentRepository.findByUserId("123a-456b")).thenReturn(daoPayments);

        this.dtoPayments = ids.stream().map(id -> createPaymentDto(Integer.toString(id)))
                .collect(Collectors.toList());

        when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
    }

    @Test
    void retrieveByUserId() {
        final String userId = "123a-456b";
        final List<PaymentDto> results = paymentService.retrieveByUserId(userId);
        assertEquals(dtoPayments, results);

        verify(paymentRepository).findByUserId(userId);
    }

    @Test
    void retrieveByUserIdNoResults() {
        final String userId = "456b-789c";
        final List<PaymentDto> results = paymentService.retrieveByUserId(userId);
        assertTrue(results.isEmpty());

        verify(paymentRepository).findByUserId(userId);
    }

    private Payment createPayment(final String id) {
        final Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

    private PaymentDto createPaymentDto(final String id) {
        final PaymentDto payment = new PaymentDto();
        payment.setId(id);
        return payment;
    }
}
