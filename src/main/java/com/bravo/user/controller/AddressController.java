package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.repository.AddressRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "api/address")
@SwaggerController
public class AddressController{


    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public List<Address> findAll() {
        return addressRepository.findAll();
    }




}
