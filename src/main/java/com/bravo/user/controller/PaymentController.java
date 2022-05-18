package com.bravo.user.controller;

import java.util.List;

import com.bravo.user.utility.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

    private final PaymentService paymentService;
    private final UserValidator userValidator;

    public PaymentController(PaymentService paymentService, UserValidator userValidator) {
        this.paymentService = paymentService;
        this.userValidator = userValidator;
    }


    @GetMapping(value = "/{userId}/retrieve")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @PathVariable(name = "userId") String userId,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse
    ) {

        userValidator.validateId(userId);
        final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
        return paymentService.retrieveByUserId(userId, pageRequest, httpResponse);

    }

}
