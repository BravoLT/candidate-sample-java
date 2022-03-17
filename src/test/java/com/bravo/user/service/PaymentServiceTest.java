package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.PaymentMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.exception.PaymentNotFoundException;
import com.bravo.user.model.dto.PaymentDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    private static final String TEST_USER_ID = "testUserId";

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void paymentNotFoundShouldThrowExceptionTest() {
        var expectedErrorMessage = "Payment not found for user with id " + TEST_USER_ID;

        when(paymentRepository.findByUserId(TEST_USER_ID)).thenReturn(Collections.emptyList());

        var exception = assertThrows(PaymentNotFoundException.class,
            () -> paymentService.findPaymentByUserId(TEST_USER_ID));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void paymentsIsFoundShouldSuccessfullyReturnPaymentsTest() {
        var testPayments = List.of(createTestPayment());
        var expectedPayments = List.of(createTestPaymentDto());

        when(paymentRepository.findByUserId(TEST_USER_ID)).thenReturn(testPayments);
        when(paymentMapper.toPaymentDtoList(testPayments)).thenReturn(expectedPayments);

        var actualPayments = paymentService.findPaymentByUserId(TEST_USER_ID);

        assertEquals(expectedPayments, actualPayments);
    }

    private Payment createTestPayment() {
        var payment = new Payment();
        payment.setCardNumber("testCardNumber");
        payment.setExpiryMonth(2);
        payment.setExpiryYear(2022);
        payment.setUpdated(LocalDateTime.now());

        return payment;
    }

    private PaymentDto createTestPaymentDto() {
        return PaymentDto.builder()
            .cardNumber("testCardNumber")
            .expiryMonth(2)
            .expiryYear(2022)
            .updated(LocalDateTime.now())
            .build();
    }
}
