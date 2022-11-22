package com.bravo.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;


@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

	  private final PaymentService pymtService;
	  private final UserValidator userValidator;

	  public PaymentController(PaymentService pymtService, UserValidator userValidator) {
	    this.pymtService = pymtService;
	    this.userValidator = userValidator;
	  }

		@GetMapping(value = "/retrieve/{userId}")
		@ResponseBody
		public List<PaymentDto> retrieve(final @PathVariable String userId) {
			userValidator.validateId(userId);
			return pymtService.retrieveByUserId(userId);
		}


}
