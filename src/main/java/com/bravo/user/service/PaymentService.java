package com.bravo.user.service;

import com.bravo.user.dao.model.mapper.PaymentMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.exception.PaymentNotFoundException;
import com.bravo.user.model.dto.PaymentDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public List<PaymentDto> findPaymentByUserId(String userId) {
        var payments = paymentRepository.findByUserId(userId);

        if(payments.isEmpty()) {
            throw new PaymentNotFoundException(userId);
        }

        return paymentMapper.toPaymentDtoList(payments);
    }

}
