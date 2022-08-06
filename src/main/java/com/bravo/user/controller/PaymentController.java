package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.PaymentValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  public List<PaymentDto> retrieve(
          final @PathVariable String userId,
      final HttpServletResponse httpResponse
  ) {
    if(userId != null){
      paymentValidator.validateId(userId);
      return paymentService.retrieve(userId);
    }
//    else if(userId != null){
//      paymentValidator.validateName(ValidatorUtil.removeControlCharacters(userId));
//      final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
//      return paymentService.findPaymentsById(name/*, pageRequest, httpResponse*/);
//    }
    else {
      throw new BadRequestException("'userId' is required!");
    }
  }
}
