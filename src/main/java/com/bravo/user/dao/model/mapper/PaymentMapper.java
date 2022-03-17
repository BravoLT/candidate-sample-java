package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.model.dto.PaymentDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "cardNumber", target = "cardNumber", qualifiedByName = "lastFourCardDigits")
    PaymentDto toPaymentDto(Payment payment);

    @Mapping(source = "cardNumber", target = "cardNumber", qualifiedByName = "lastFourCardDigits")
    List<PaymentDto> toPaymentDtoList(List<Payment> payments);

    @Named("lastFourCardDigits")
    static String lastFourCardDigits(String cardNumber) {
        return cardNumber.replaceAll("\\w(?=\\w{4})", "*");
    }
}
