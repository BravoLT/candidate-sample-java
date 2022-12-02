package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.UserValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

    private final PaymentService paymentService;

    private final UserValidator userValidator;

    public PaymentController(PaymentService paymentService, UserValidator userValidator) {
        this.paymentService = paymentService;
        this.userValidator = userValidator;
    }

    @GetMapping(value = "/user/{id}")
    @ResponseBody
    public List<PaymentDto> retrieveUserPayments(
            final @PathVariable String id,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse) {
        userValidator.validateId(id);
        final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
        return paymentService.retrievePayments(id, pageRequest, httpResponse);
    }
}