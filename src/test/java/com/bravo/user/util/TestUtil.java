package com.bravo.user.util;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.dto.UserSaveDto;

import java.time.LocalDateTime;
import java.util.List;

public class TestUtil {

    public static User getUserData() {

        UserSaveDto userDto = new UserSaveDto();

        userDto.setFirstName("Harrison");
        userDto.setMiddleName("");
        userDto.setLastName("Ford");
        userDto.setPhoneNumber("");

        return new User(userDto);
    }

    public static List<Payment> getPayments() {
        Payment payment = new Payment();

        payment.setId("15040055-a640-4cea-cfb7-357669fcdefe");
        payment.setUserId("fd6d21f6-f1c2-473d-8ed7-f3f9c7550cc9");
        payment.setCardNumber("4790580235733205");
        payment.setExpiryMonth(8);
        payment.setExpiryYear(2028);
        payment.setUpdated(LocalDateTime.of(2023, 1, 25, 1, 25, 0));

        return List.of(payment);
    }

    public static List<PaymentDto> getPaymentsDto() {
        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setId("15040055-a640-4cea-cfb7-357669fcdefe");
        paymentDto.setExpiryMonth(8);
        paymentDto.setExpiryYear(2028);
        paymentDto.setUpdated(LocalDateTime.of(2023, 1, 25, 1, 25, 0));
        paymentDto.setCardNumberLast4("3205");

        return List.of(paymentDto);
    }
}
