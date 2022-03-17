package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.model.dto.PaymentDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PaymentMapperImpl.class)
public class PaymentMapperTest {

    private static final String TEST_CARD_NUMBER = "123456789";
    private static final String MASKED_TEST_CARD_NUMBER = "*****6789";
    private static final int TEST_EXPIRY_MONTH = 2;
    private static final int TEST_EXPIRY_YEAR = 2022;
    private static final LocalDateTime TEST_UPDATED = LocalDateTime.now();

    @Autowired
    private PaymentMapper paymentMapper;

    @Test
    public void shouldSuccessfullyMapPaymentToPaymentDtoTest() {
        var paymentDto = paymentMapper.toPaymentDto(createTestPayment());
        var expectedPaymentDto = createTestPaymentDto();
        assertEquals(expectedPaymentDto, paymentDto);
    }

    @Test
    public void shouldSuccessfullyMapPaymentListToPaymentDtoListTest() {
        var paymentDtoList = paymentMapper.toPaymentDtoList(List.of(createTestPayment()));
        var expectedPaymentDtoList = List.of(createTestPaymentDto());
        assertEquals(expectedPaymentDtoList, paymentDtoList);
    }

    @Test
    public void shouldSuccessfullyMaskCardNumberTest() {
        var actualMaskedCard = PaymentMapper.lastFourCardDigits(TEST_CARD_NUMBER);

        assertEquals(MASKED_TEST_CARD_NUMBER, actualMaskedCard);
    }

    private Payment createTestPayment() {
        var payment = new Payment();
        payment.setCardNumber(TEST_CARD_NUMBER);
        payment.setExpiryMonth(TEST_EXPIRY_MONTH);
        payment.setExpiryYear(TEST_EXPIRY_YEAR);
        payment.setUpdated(TEST_UPDATED);

        return payment;
    }

    private PaymentDto createTestPaymentDto() {
        return PaymentDto.builder()
            .expiryYear(TEST_EXPIRY_YEAR)
            .expiryMonth(TEST_EXPIRY_MONTH)
            .cardNumber(MASKED_TEST_CARD_NUMBER)
            .updated(TEST_UPDATED)
            .build();
    }
}
