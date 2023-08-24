package com.bravo.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name="Payment", description="Payment Actions")
@RequestMapping(value = "/payment")
public class PaymentController {
    private final PaymentService paymentService;
	private final UserValidator userValidator; 
    
    public PaymentController(PaymentService paymentService, UserValidator userValidator) {
		this.paymentService = paymentService;
		this.userValidator = userValidator;
	}

  @Operation(summary = "Retrieve User payment Information")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
  @GetMapping(value = "/retrieve/{userId}")
  @ResponseBody
  public List<PaymentDto> retrieve(
      final @PathVariable String userId,
      final HttpServletResponse httpResponse
  ) 
  {
    log.info("Userid:{}", userId);
    userValidator.validateId(userId);
    return paymentService.retrieveByUserId(userId);
  }
    
}
