package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/retrieve/{userId}")
    @ResponseBody
    public List<PaymentDto> retrieve(final @PathVariable String userId) {

        // TODO: validate userId

        return paymentService.retrieveByUserId(userId);
    }
}
