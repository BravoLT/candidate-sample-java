package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.PaymentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private final PaymentRepository paymentRepository;
    private final ResourceMapper resourceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper) {
        this.paymentRepository = paymentRepository;
        this.resourceMapper = resourceMapper;
    }

    public List<PaymentDto> retrieveByUserID(String id) {
        LOGGER.warn("Payments for user DB : "+id);
       List<Payment> optionalPayments = paymentRepository.findByUserId(id);
       List<PaymentDto> payments = resourceMapper.convertPayments(optionalPayments);
        LOGGER.warn("Payments from DB : "+optionalPayments);
        return payments;
    }

    private Collection<Payment> getPayment(Optional<List<Payment>> payments) {
        if(payments.isEmpty()){
            final String message = String.format("payments '%s' doesn't exist", payments);
            LOGGER.warn(message);
            throw new DataNotFoundException(message);
        }
        return payments.get();
    }
}
