package com.bravo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.PaymentDto;

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

    @MockBean
    private UserRepository userRepository;

    private List<PaymentDto> dtoPayments;

    @BeforeEach
    public void beforeEach() {
        final List<Integer> ids = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) ids.add(i);

        final List<Payment> daoPayments = ids.stream().map(id -> createPaymentDao(Integer.toString(id))).collect(Collectors.toList()); 

        this.dtoPayments = ids.stream().map(id -> createPaymentDto(Integer.toString(id))).collect(Collectors.toList());

        when(paymentRepository.findByUserId(anyString())).thenReturn(daoPayments);
        when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
    }

    @Test
    void retrieveByUserId() {
        final String userId = "0809r-0427n";
        final List<PaymentDto> results = paymentService.retrieveByUserId(userId);
        final Optional<User> user = Optional.of(createUser(userId));

        when(userRepository.findById(anyString())).thenReturn(user);

        assertEquals(dtoPayments, results);
        verify(userRepository).findById(userId);
        verify(paymentRepository).findByUserId(userId);
    }

    @Test
    void retrieveByUserId_DataNotFoundException() {
        final String userId = "0809n-0427r";

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> paymentService.retrieveByUserId(userId));
        verify(userRepository).findById(userId);
    }

    private PaymentDto createPaymentDto(final String id) {
        final PaymentDto payment = new PaymentDto();
        payment.setId(id);
        return payment;
    }
    
    private Payment createPaymentDao(final String id) {
        final Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

    private User createUser(final String id){
        final User user = new User();
        user.setId(id);
        return user;
    }
}
