package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ResourceMapper mapper;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository, ResourceMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<PaymentDto> retrieveByUserId(String userId) {
        log.info("Retrieving all payments by user Id");

        userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User Id does not exist. Please validate input"));
        final List<Payment> payments = paymentRepository.findAllByUserId(userId);

        log.info("Found {} payments for user {}", payments.size(), userId);
        return mapper.convertPayments(payments);
    }
}
