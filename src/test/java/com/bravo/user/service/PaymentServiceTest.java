package com.bravo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import com.bravo.user.App;
import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.model.dto.AddressDto;

@ContextConfiguration(classes = { App.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private ResourceMapper resourceMapper;

    @MockBean
    private PaymentRepository paymentRepository;

    private List<PaymentDto> dtoPayment;

    @BeforeEach
    public void beforeEach() {
        final List<Integer> ids = IntStream.range(1, 10).boxed().collect(Collectors.toList());

        final List<Payment> daoPayment = ids.stream()
                .map(id -> createAddress(Integer.toString(id))).collect(Collectors.toList());

        when(paymentRepository.findByUserId(anyString())).thenReturn(daoPayment);

        this.dtoPayment = ids.stream().map(id -> createAddressDto(Integer.toString(id)))
                .collect(Collectors.toList());

        when(resourceMapper.convertPayments(daoPayment)).thenReturn(dtoPayment);
    }

    @Test
    void retrieveByUserId() {
        final String userId = "123a-456b";
        final List<PaymentDto> results = paymentService.retrieveByUserID(userId);
        assertEquals(dtoPayment, results);

        verify(paymentRepository).findByUserId(userId);
    }

    private Payment createAddress(final String id) {
        final Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

    private PaymentDto createAddressDto(final String id) {
        final PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(id);
        return paymentDto;
    }

}
