package com.bravo.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import com.bravo.user.validator.UserValidator;
//I understand the controllers and that they handle HTTP requests
//this controller specifically is handling requests related to Addresses
@RequestMapping(value = "/address")
@SwaggerController
public class AddressController {

	private final AddressService addressService;
	private final UserValidator userValidator;
	//constructor for AddressController class
	public AddressController(AddressService addressService, UserValidator userValidator) {
		this.addressService = addressService;
		this.userValidator = userValidator;
	}
	//here we will pass a user id to the endpoint and recieve a response
	//@PathVariable annotation marks the userId as the required argument
	//I have only seen @RequestMapping(method="GET") but as I understand, @GetMapping does the same thing
	@GetMapping(value = "/retrieve/{userId}")
	@ResponseBody
	public List<AddressDto> retrieve(final @PathVariable String userId) {
		userValidator.validateId(userId);
		return addressService.retrieveByUserId(userId);
	}

}
