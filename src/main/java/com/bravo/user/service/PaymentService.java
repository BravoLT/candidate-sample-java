package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.utility.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ResourceMapper resourceMapper;

    public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper) {
        this.paymentRepository = paymentRepository;
        this.resourceMapper = resourceMapper;

    }

    public List<PaymentDto> retrieveByUserId(String userId,
                                             final PageRequest pageRequest,
                                             final HttpServletResponse httpResponse) {
        Page<Payment> paymentPage = paymentRepository.findByUserId(userId, pageRequest);
        List<PaymentDto> payments = resourceMapper.convertPayments(paymentPage.getContent());
        PageUtil.updatePageHeaders(httpResponse, paymentPage, pageRequest);
        return payments;
    }


}
