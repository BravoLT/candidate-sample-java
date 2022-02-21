package com.bravo.user.service;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.model.dto.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    private AddressService testObj;
    @Mock
    private AddressRepository repository;
    @Autowired
    private ResourceMapper mapper;

    @BeforeEach
    void setUp() {
        testObj = new AddressService(repository, mapper);
    }

    @Test
    void findAllShouldCallRepositoryMethodAndMatchZip() {
        Address address = new Address();
        String zip = "test zip";
        address.setZip(zip);
        when(repository.findAll(any(Specification.class))).thenReturn(List.of(address));
        List<AddressDto> results = testObj.findAllByUserId("");
        assertEquals(zip, results.get(0).getZip());
        verify(repository).findAll(any(Specification.class));
    }
}