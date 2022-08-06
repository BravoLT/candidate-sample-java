package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.utility.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

  public List <PaymentDto> retrieveByUserId(
          final String userId,
          final PageRequest pageRequest,
          final HttpServletResponse httpResponse
  ){

    final Page<Payment> paymentPage = paymentRepository.findPaymentsByUserId(userId, pageRequest);
    final List<PaymentDto> payments = resourceMapper.convertPayments(paymentPage.getContent());
    LOGGER.info("found {} payment(s) for user '{}'", payments.size(), userId);
    PageUtil.updatePageHeaders(httpResponse, paymentPage, pageRequest);
    return payments;
  }
}
