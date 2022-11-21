package com.bravo.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bravo.user.dao.model.Address;
import com.bravo.common.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.model.dto.AddressDto;

@Service
public class AddressService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

	private final AddressRepository addressRepository;
	private final ResourceMapper resourceMapper;

	public AddressService(AddressRepository addressRepository, ResourceMapper resourceMapper) {
		this.addressRepository = addressRepository;
		this.resourceMapper = resourceMapper;
	}

	public List<AddressDto> retrieveByUserId(final String userId) {
		final List<Address> addressList = addressRepository.findByUserId(userId);
		LOGGER.info("found {} address(es)", addressList.size());

		return resourceMapper.convertAddresses(addressList);
	}
}
