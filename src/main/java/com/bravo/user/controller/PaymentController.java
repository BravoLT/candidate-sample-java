package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.PaymentValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

  private final PaymentService paymentService;
  private final PaymentValidator paymentValidator;

  public PaymentController(PaymentService paymentService, PaymentValidator paymentValidator) {
    this.paymentService = paymentService;
    this.paymentValidator = paymentValidator;
  }

  @GetMapping(value = "/retrieve/{userId}")
  @ResponseBody
  public List<PaymentDto> getPaymentsForUserId(
          final @PathVariable @RequestPart String userId,
          final @RequestParam(required = false) Integer page,
          final @RequestParam(required = false) Integer size,
          final HttpServletResponse httpResponse
  ) {
    paymentValidator.validateId(userId);
    return paymentService.retrieveByUserId(
            userId
            , PageUtil.createPageRequest(page, size)
            , httpResponse);
  }
}
