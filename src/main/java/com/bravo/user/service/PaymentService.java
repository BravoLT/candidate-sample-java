package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final PaymentRepository paymentRepository;
  private final ResourceMapper resourceMapper;

  public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper) {
    this.paymentRepository = paymentRepository;
    this.resourceMapper = resourceMapper;
  }

  public List<PaymentDto> retrieve(final String id){
    List<Payment> list = paymentRepository.findPaymentsByUserId(id);
    LOGGER.info("found {} payment(s) for user '{}'", list.size(), id);
    return resourceMapper.convertPayments(list);
  }
}
