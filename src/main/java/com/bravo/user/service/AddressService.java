package com.bravo.user.service;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.model.dto.AddressDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

  private final AddressRepository addressRepository;
  private final ResourceMapper resourceMapper;

  public AddressService(AddressRepository addressRepository, ResourceMapper resourceMapper) {
    this.addressRepository = addressRepository;
    this.resourceMapper = resourceMapper;
  }

  public List<AddressDto> retrieve(final String id){
    final var addresses = addressRepository.findByUserId(id);
    LOGGER.info("found {} addresses for user '{}'", addresses.size(), id);
    return addresses.stream().map(resourceMapper::convertAddress).collect(Collectors.toList());
  }
}
