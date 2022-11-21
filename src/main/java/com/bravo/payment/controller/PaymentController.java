package com.bravo.payment.controller;

import com.bravo.payment.service.PaymentService;
import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.exception.BadRequestException;
import com.bravo.payment.model.dto.PaymentReadDto;
import com.bravo.common.utility.PageUtil;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bravo.user.validator.UserValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  /**
   * Returns all payments made by a given user by their userId
   */
  @ApiOperation("Retrieve payments made by a user")
  @GetMapping(value = "/retrieve/{userId}")
  @ResponseBody
  public List<PaymentReadDto> retrieve(
      final @PathVariable String userId,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    if (userId != null) {
      userValidator.validateId(userId);
      final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
      return paymentService.retrieveByUserId(userId, pageRequest, httpResponse);
    } else {
      throw new BadRequestException("'userId' is required");
    }
  }
}
