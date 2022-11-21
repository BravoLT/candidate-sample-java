package com.bravo.payment.service;

import com.bravo.common.dao.model.mapper.ResourceMapper;
import com.bravo.common.utility.PageUtil;
import com.bravo.payment.dao.model.Payment;
import com.bravo.payment.dao.repository.PaymentRepository;
import com.bravo.payment.model.dto.PaymentReadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class PaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

  private final PaymentRepository paymentRepository;
  private final ResourceMapper resourceMapper;

  public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper) {
    this.paymentRepository = paymentRepository;
    this.resourceMapper = resourceMapper;
  }

  public List<PaymentReadDto> retrieveByUserId(
      final String userId,
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  ) {
    final Page<Payment> paymentPage = paymentRepository.findByUserId(userId, pageRequest);
    final List<PaymentReadDto> payments = resourceMapper.convertPayments(paymentPage.getContent());
    LOGGER.info("found {} payment(s)", payments.size());

    PageUtil.updatePageHeaders(httpResponse, paymentPage, pageRequest);
    return payments;
  }
}
