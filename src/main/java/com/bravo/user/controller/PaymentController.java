package com.bravo.user.controller;

import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.PaymentValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Payment", description = "Payment Actions")
@RequestMapping(value = "/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentValidator paymentValidator;
    public PaymentController(PaymentService paymentService, PaymentValidator paymentValidator) {
        this.paymentService = paymentService;
        this.paymentValidator = paymentValidator;
    }

    @Operation(summary = "Retrieve Payment Information")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    @GetMapping(value = "/retrieve/{userId}")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @PathVariable String userId
    ) {
        paymentValidator.validateUserId(userId);
        return paymentService.retrieveByUserID(userId);
    }
}
