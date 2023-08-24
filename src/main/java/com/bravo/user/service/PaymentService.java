package com.bravo.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
	private final ResourceMapper resourceMapper;
    
    public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper) {
		this.paymentRepository = paymentRepository;
		this.resourceMapper = resourceMapper;
	}

    public List<PaymentDto> retrieveByUserId(final String userId) {
		final List<Payment> paymentList = paymentRepository.findByUserId(userId);
		log.info("found {} payment(s)", paymentList.size());

		return resourceMapper.convertPayments(paymentList);
	}

}
