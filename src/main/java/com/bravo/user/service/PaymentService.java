package com.bravo.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.PaymentDto;

@Service
public class PaymentService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ResourceMapper resourceMapper;

    public PaymentService(
        PaymentRepository paymentRepository, 
        UserRepository userRepository,
        ResourceMapper resourceMapper) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.resourceMapper = resourceMapper;
    }

    public List<PaymentDto> retrieveByUserId(final String userId) {
        // check if user exists
        if (userRepository.findById(userId).isEmpty()) {
            final String message = String.format("user '%s' doesn't exist", userId);
            LOGGER.warn(message);
            throw new DataNotFoundException(message);
        }

        final List<Payment> payments = paymentRepository.findByUserId(userId);
      
        LOGGER.info("found {} payment(s)", payments.size());

        return resourceMapper.convertPayments(payments);
    }
}
