package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class AddressControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AddressService addressService;

  private AddressDto address1;
  private AddressDto address2;

  @BeforeEach
  public void beforeEach(){
    this.address1 = createAddress("1");
    this.address2 = createAddress("2");
  }

  @Test
  void getRetrieveWithIdEmpty() throws Exception {
    this.mockMvc.perform(get("/user/ /address/retrieve"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithId() throws Exception {
    String id = "5";
    when(addressService.retrieve(id)).thenReturn(List.of(address1));
    final ResultActions result = this.mockMvc
            .perform(get("/user/5/address/retrieve"))
            .andExpect(status().isOk());
    result.andExpect(jsonPath("$[0].id").value(address1.getId()));
  }

  @Test
  void getRetrieveWithIdMultipleAddress() throws Exception {
    String id = "6";
    when(addressService.retrieve(id)).thenReturn(List.of(address1, address2));
    final ResultActions result = this.mockMvc
            .perform(get("/user/6/address/retrieve"))
            .andExpect(status().isOk());
    result.andExpect(jsonPath("$[0].id").value(address1.getId()));
    result.andExpect(jsonPath("$[1].id").value(address2.getId()));
  }

  AddressDto createAddress(final String id) {
    final AddressDto addressDto = new AddressDto();
    addressDto.setId(id);
    return addressDto;
  }

}