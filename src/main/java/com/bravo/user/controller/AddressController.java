package com.bravo.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.AddressValidator;
import com.bravo.user.validator.UserValidator;

@RequestMapping(value = "/address")
@SwaggerController
public class AddressController {

	private final AddressService addressService;
	private final AddressValidator addressValidator;
	private final UserValidator userValidator;

	public AddressController(AddressService addressService, AddressValidator addressValidator,
			UserValidator userValidator) {
		this.addressService = addressService;
		this.addressValidator = addressValidator;
		this.userValidator = userValidator;
	}

	@GetMapping(value = "/retrieve/{userId}")
	@ResponseBody
	public List<AddressDto> retrieve(final @PathVariable String userId,
			final @RequestParam(required = false) Integer page,
			final @RequestParam(required = false) Integer size,
			final HttpServletResponse httpResponse) {
		userValidator.validateId(userId);

		// one-to-many addresses per user, so using pagination
		final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
		return addressService.retrieveByUserId(userId, pageRequest, httpResponse);
	}

}
