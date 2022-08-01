package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.dto.UserReadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

  private final PaymentRepository paymentRepository;
  private final ResourceMapper resourceMapper;
  private final UserService userService;

  public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper,
                        UserService userService) {
    this.paymentRepository = paymentRepository;
    this.resourceMapper = resourceMapper;
    this.userService = userService;
  }

  public List<PaymentDto> retrieve(final String id){
    UserReadDto user = userService.retrieve(id);

    List<Payment> payments = paymentRepository.findByUserId(user.getId());
    LOGGER.info("found {} payment(s)", payments.size());
    return resourceMapper.convertPayments(payments);
  }


}
