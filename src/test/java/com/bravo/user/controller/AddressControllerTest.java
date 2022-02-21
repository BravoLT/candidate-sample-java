package com.bravo.user.controller;

import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AddressController.class)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AddressService service;

    @Test
    void getShouldReturnAddressWithMatchingZip() throws Exception {
        AddressDto address = new AddressDto();
        address.setZip("test zip");
        AddressDto address1 = new AddressDto();
        address1.setZip("test zip 1");
        when(service.findAllByUserId("testId")).thenReturn(List.of(address, address1));
        mockMvc.perform(get("/user/testId/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zip").value("test zip"))
                .andExpect(jsonPath("$[1].zip").value("test zip 1"));
    }

    @Test
    void emptyIdShouldNotFindAnything() throws Exception {
        mockMvc.perform(get("/user/address")).andExpect(status().isNotFound());
    }

    @Test
    void noDataShouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/user/test id/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}