package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class PaymentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private List<PaymentDto> paymentDtoList;

    @BeforeEach
    void setUp() {
        final List<Integer> ids = IntStream
                .range(1, 10)
                .boxed()
                .collect(Collectors.toList());

        paymentDtoList = ids.stream()
                .map(id -> createPaymentDto(Integer.toString(id)))
                .collect(Collectors.toList());
    }

    @Test
    void getPaymentMethodsForUserId() throws Exception {
        when(paymentService.retrieveByUserId(anyString())).thenReturn(paymentDtoList);

        final String userId = UUID.randomUUID().toString();

        final ResultActions result = mockMvc.perform(get("/payment/retrieve/" + userId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < paymentDtoList.size(); i++) {
            result.andExpect(jsonPath(String.format("$[%d].id", i)).value(paymentDtoList.get(i).getId()));
        }

        verify(paymentService, times(1)).retrieveByUserId(anyString());

    }

    @Test
    void getWithMissingUserId() throws Exception {
        mockMvc.perform(get("/payment/retrieve/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getWithSpaceAsUserId() throws Exception {
        mockMvc.perform(get("/payment/retrieve/ /"))
                .andExpect(status().isBadRequest());
    }

    private PaymentDto createPaymentDto(String id) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(id);
        return paymentDto;
    }

}
