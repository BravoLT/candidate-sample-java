package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/user")
@SwaggerController
public class AddressController {
    private final AddressService addressService;

    @ResponseBody
    @GetMapping(value = "/{userId}/address")
    List<AddressDto> findAll(@PathVariable String userId) {
        return addressService.findAllByUserId(userId);
    }
}
