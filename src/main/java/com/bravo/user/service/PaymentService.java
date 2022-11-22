package com.bravo.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;

/**
 * Payment Service
 * 
 * @author Bob Wilson
 * Created November 2022
 * 
 */
@Service
public class PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

	private final PaymentRepository pymtRepository;
	private final ResourceMapper resourceMapper;

	public PaymentService(PaymentRepository pymtRepository, ResourceMapper resourceMapper) {
		this.pymtRepository = pymtRepository;
		this.resourceMapper = resourceMapper;
	}

	/*
	 * Retrieve all payments by a user.
	 * 
	 * @param userId
	 * @return paymentList	
	 */
	public List<PaymentDto> retrieveByUserId(final String userId) {
		final List<Payment> paymentList = pymtRepository.findByUserId(userId);
		LOGGER.info("found {} payment(s)", paymentList.size());

		return resourceMapper.convertPayments(paymentList);
	}

}
