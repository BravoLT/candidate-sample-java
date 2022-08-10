package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.specification.PaymentSpecification;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.filter.PaymentFilter;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final ResourceMapper resourceMapper;

    public PaymentService(
            PaymentRepository paymentRepository,
            ResourceMapper resourceMapper
    ){
        this.paymentRepository = paymentRepository;
        this.resourceMapper = resourceMapper;
    }

    public List<PaymentDto> retrievePaymentByUserId(
            final String userId,
            final PageRequest pageRequest,
            final HttpServletResponse httpResponse
    ){
        return retrieve(PaymentFilter.builder().userId(userId).build(), pageRequest, httpResponse);
    }

    public List<PaymentDto> retrieve(
            final PaymentFilter filter,
            final PageRequest pageRequest,
            final HttpServletResponse httpResponse
    ){
        LOGGER.debug("Request to retrieve payment information being conducted... paymentFilter: {}", filter);
        final PaymentSpecification specification = new PaymentSpecification(filter);
        final Page<Payment> paymentPage = paymentRepository.findAll(specification,pageRequest);
        final List<PaymentDto> payments = resourceMapper.convertPayments(paymentPage.getContent());
        LOGGER.debug("Found {} payment(s)", payments.size());

        PageUtil.updatePageHeaders(httpResponse, paymentPage, pageRequest);
        return payments;
    }
}
