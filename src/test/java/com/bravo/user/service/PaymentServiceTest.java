package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
class PaymentServiceTest {

    private final PaymentRepository paymentRepository = mock(PaymentRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ResourceMapper resourceMapper = mock(ResourceMapper.class);

    private final PaymentService underTest = new PaymentService(
            paymentRepository,
            userRepository,
            resourceMapper
    );

    @Test
    void retrieveByUserId() {
        final String userId = "user-id";

        when(userRepository.findById(userId)).thenReturn(Optional.of(TestUtil.getUserData()));
        when(paymentRepository.findAllByUserId(userId)).thenReturn(TestUtil.getPayments());
        when(resourceMapper.convertPayments(TestUtil.getPayments())).thenReturn(TestUtil.getPaymentsDto());

        List<PaymentDto> payments = underTest.retrieveByUserId(userId);

        assertNotNull(payments);
        assertEquals(payments.size(), 1);
    }

    @Test
    void retrieveByUserIdNotPaymentsFound() {
        final String userId = "user-id";

        when(userRepository.findById(userId)).thenReturn(Optional.of(TestUtil.getUserData()));
        when(paymentRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());
        when(resourceMapper.convertPayments(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<PaymentDto> payments = underTest.retrieveByUserId(userId);

        assertNotNull(payments);
        assertEquals(payments.size(), 0);
    }

    @Test
    void retrieveByUserIdNotFound() {
        final String userId = "Not found";

        when(userRepository.findById(userId)).thenThrow(DataNotFoundException.class);

        DataNotFoundException thrown = assertThrows(DataNotFoundException.class, () -> underTest.retrieveByUserId(userId));

        assertNotNull(thrown);
    }
}