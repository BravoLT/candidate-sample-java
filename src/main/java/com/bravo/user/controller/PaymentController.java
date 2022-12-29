package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

  private final PaymentService paymentService;
  private final UserValidator userValidator;

  public PaymentController(PaymentService paymentService, UserValidator userValidator) {
    this.paymentService = paymentService;
    this.userValidator = userValidator;
  }

  @GetMapping(value = "/retrieve/{userId}")
  @ResponseBody
  public List<PaymentDto> retrieve(
      final @PathVariable String userId,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    userValidator.validateId(userId);
    return paymentService.retrieveByUserId(userId);
  }
}
