package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.AddressRepository;
import com.bravo.user.dao.specification.UserNameFuzzySpecification;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.filter.UserNameFuzzyFilter;
import com.bravo.user.utility.PageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddressServiceTest {

  @Autowired
  private HttpServletResponse httpResponse;

  @Autowired
  private AddressService addressService;

  @MockBean
  private ResourceMapper resourceMapper;

  @MockBean
  private AddressRepository addressRepository;

  private Address address1;
  private AddressDto addressDto1;
  private Address address2;
  private AddressDto addressDto2;

  @BeforeEach
  public void beforeEach(){
    address1 = createAddress("1");
    addressDto1 = createAddressDto("1");
    address2 = createAddress("2");
    addressDto2 = createAddressDto("2");

    when(resourceMapper.convertAddress(address1))
            .thenReturn(addressDto1);
    when(resourceMapper.convertAddress(address2))
            .thenReturn(addressDto2);
  }

  @Test
  public void retrieveNoAddresses() {
    final var userId = "5";
    when(addressRepository.findByUserId(userId))
            .thenReturn(Collections.emptyList());

    final var result = addressService.retrieve(userId);
    assertEquals(0, result.size());
  }

  @Test
  public void retrieveOneAddresses() {
    final var userId = "5";
    when(addressRepository.findByUserId(userId))
            .thenReturn(List.of(address1));

    final var result = addressService.retrieve(userId);
    assertEquals(1, result.size());
    assertEquals("1", result.get(0).getId());
  }

  @Test
  public void retrieveMultipleAddresses() {
    final var userId = "5";
    when(addressRepository.findByUserId(userId))
            .thenReturn(List.of(address1, address2));

    final var result = addressService.retrieve(userId);
    assertEquals(2, result.size());
    final var ids = List.of("1", "2");
    assertTrue(ids.contains(result.get(0).getId()));
    assertTrue(ids.contains(result.get(1).getId()));
  }

  private Address createAddress(final String id){
    final Address address = new Address();
    address.setId(id);
    return address;
  }

  private AddressDto createAddressDto(final String id){
    final AddressDto addressDto = new AddressDto();
    addressDto.setId(id);
    return addressDto;
  }

}