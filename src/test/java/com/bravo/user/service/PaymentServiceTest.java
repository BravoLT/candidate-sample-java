package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @MockBean
    private PaymentRepository paymentRepository;

    private List<Payment> payments;

    @BeforeEach
    public void beforeEach() {
        this.payments = createPayments();
    }

    @Test
    public void retrieveByUserIdName() {
        when(paymentRepository.findByUserId(anyString())).thenReturn(payments);
        List<PaymentDto> paymentDtos = paymentService.retrieveByUserId("userId");

        assertEquals(paymentDtos.size(), 2);
        assertEquals(paymentDtos.get(1).getCardNumberLast4(), "8888");

    }

    private List<Payment> createPayments() {

        List<Payment> payments = new ArrayList<>();

        final Payment payment1 = new Payment();
        payment1.setId("444");
        payment1.setCardNumber("1111222233334444");
        payment1.setExpiryYear(2026);
        payment1.setExpiryMonth(4);
        payment1.setUpdated(LocalDateTime.now());

        final Payment payment2 = new Payment();
        payment2.setId("444");
        payment2.setCardNumber("5555666677778888");
        payment2.setExpiryYear(2036);
        payment2.setExpiryMonth(6);
        payment2.setUpdated(LocalDateTime.now());

        payments.add(payment1);
        payments.add(payment2);

        return payments;
    }
}