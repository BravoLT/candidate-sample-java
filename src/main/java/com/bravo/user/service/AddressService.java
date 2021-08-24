package com.bravo.user.service;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.dao.specification.AddressSpecification;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.AddressReadDto;
import com.bravo.user.model.filter.AddressFilter;
import com.bravo.user.utility.PageUtil;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

  private final AddressRepository addressRepository;
  private final ResourceMapper resourceMapper;

  public AddressService(AddressRepository addressRepository, ResourceMapper resourceMapper) {
    this.addressRepository = addressRepository;
    this.resourceMapper = resourceMapper;
  }

  public List<AddressReadDto> retrieve(final String userId){
    List<Address> addresses = addressRepository.findByUserId(userId);
    
    if(addresses.isEmpty()){
        final String message = String.format("address '%s' doesn't exist", userId);
        LOGGER.warn(message);
        throw new DataNotFoundException(message);
    }

    LOGGER.info("found address '{}'", userId);
    return resourceMapper.convertAddresses(addresses);
  }

  public List<AddressReadDto> retrieve(
      final AddressFilter filter,
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  ){
    final AddressSpecification specification = new AddressSpecification(filter);
    final Page<Address> addressPage = addressRepository.findAll(specification, pageRequest);
    final List<AddressReadDto> addresses = resourceMapper.convertAddresses(addressPage.getContent());
    LOGGER.info("found {} address(s)", addresses.size());

    PageUtil.updatePageHeaders(httpResponse, addressPage, pageRequest);
    return addresses;
  }

}
