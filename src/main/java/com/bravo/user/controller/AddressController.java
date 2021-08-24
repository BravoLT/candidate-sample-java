package com.bravo.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.AddressReadDto;
import com.bravo.user.model.filter.AddressFilter;
import com.bravo.user.service.AddressService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.AddressValidator;

@RequestMapping(value = "/address")
@SwaggerController
public class AddressController {

  private final AddressService addressService;
  private final AddressValidator addressValidator;

  public AddressController(AddressService addressService, AddressValidator addressValidator) {
    this.addressService = addressService;
    this.addressValidator = addressValidator;
  }

  @GetMapping(value = "/retrieve/{userId}")
  @ResponseBody
  public List<AddressReadDto> retrieve(@PathVariable String userId){
    return addressService.retrieve(userId);
  }

  @PostMapping(value = "/retrieve")
  @ResponseBody
  public List<AddressReadDto> retrieve(
      final @RequestBody AddressFilter filter,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ){
    final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
    return addressService.retrieve(filter, pageRequest, httpResponse);
  }

}
