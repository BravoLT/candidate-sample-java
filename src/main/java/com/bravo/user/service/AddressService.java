package com.bravo.user.service;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.dao.specification.AddressSpecification;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.model.filter.AddressFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final ResourceMapper resourceMapper;

    public List<AddressDto> findAllByUserId(String userId) {
        AddressFilter filter = AddressFilter.builder()
                .userId(Set.of(userId))
                .build();
        AddressSpecification specification = new AddressSpecification(filter);
        List<Address> addresses = addressRepository.findAll(specification);
        return resourceMapper.convertAddresses(addresses);
    }
}
