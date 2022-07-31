package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;
import org.springframework.web.bind.annotation.*;

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


  @GetMapping(value = "/retrieve")
  @ResponseBody
  public List<PaymentDto> retrieve(
      final @RequestParam String userId
  ) {
    if(userId != null){
      userValidator.validateId(userId);
      return paymentService.retrieve(userId);
    }
    else {
      throw new BadRequestException("'userId' is required!");
    }
  }

}
