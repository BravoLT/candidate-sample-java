package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import com.bravo.user.validator.UserValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/user/{id}/address")
@SwaggerController
public class AddressController {

  private final AddressService addressService;
  private final UserValidator userValidator;

  public AddressController(AddressService addressService, UserValidator userValidator) {
    this.addressService = addressService;
    this.userValidator = userValidator;
  }

  @GetMapping(value = "/retrieve")
  @ResponseBody
  public List<AddressDto> retrieve(
      final @PathVariable String id,
      final HttpServletResponse httpResponse
  ) {
    if(id == null) {
      throw new BadRequestException("'id' is required!");
    }
    userValidator.validateId(id);
    return addressService.retrieve(id);
  }

}
