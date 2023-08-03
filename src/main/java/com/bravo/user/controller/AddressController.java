package com.bravo.user.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import com.bravo.user.validator.UserValidator;

@RestController
@Tag(name = "Addresses", description = "Addresses of Users")
@RequestMapping(value = "/address")
public class AddressController {

	private final AddressService addressService;
	private final UserValidator userValidator;

	public AddressController(AddressService addressService, UserValidator userValidator) {
		this.addressService = addressService;
		this.userValidator = userValidator;
	}

	@Operation(summary = "Retrieve Address by User id")
	@ApiResponse(responseCode = "200", content = {
					@Content(schema= @Schema(implementation = List.class), mediaType = "application/json")
	})
	@ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
	@GetMapping(value = "/retrieve/{userId}")
	@ResponseBody
	public List<AddressDto> retrieve(final @PathVariable String userId) {
		userValidator.validateId(userId);
		return addressService.retrieveByUserId(userId);
	}

}
