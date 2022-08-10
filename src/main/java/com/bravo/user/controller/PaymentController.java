package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.filter.PaymentFilter;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

    private final UserValidator userValidator;
    private final PaymentService paymentService;

    public PaymentController(
            UserValidator userValidator,
            PaymentService paymentService
    ){
        this.userValidator = userValidator;
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/retrieve")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @RequestParam String userId,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse
    ){
        userValidator.validateId(userId);
        final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
        return paymentService.retrievePaymentByUserId(userId, pageRequest, httpResponse);
    }

    @PostMapping(value = "/retrieve")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @RequestBody PaymentFilter filter,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse
    ){
        final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
        return paymentService.retrieve(filter, pageRequest, httpResponse);
    }
}
