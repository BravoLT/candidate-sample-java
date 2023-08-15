package com.bravo.user.controller;

import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Payment", description = "User payments")
@RequestMapping(value = "/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final UserValidator userValidator;

    public PaymentController(PaymentService paymentService, UserValidator userValidator) {
        this.paymentService = paymentService;
        this.userValidator = userValidator;
    }

    @Operation(summary = "Retrieves a list of payments by User")
    @GetMapping("/retrieve/{userId}")
    @ResponseBody
    public List<PaymentDto> retrievePaymentsByUser(final @PathVariable String userId) {

        log.info("Retrieving payments for user Id {}", userId);

        userValidator.validateId(userId);
        return paymentService.retrieveByUserId(userId);
    }
}
