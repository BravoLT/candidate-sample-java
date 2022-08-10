package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.filter.PaymentFilter;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class PaymentControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;

    private List<PaymentDto> payments;
    private PaymentFilter paymentFilter;

    @BeforeEach
    public void beforeEach(){
        this.payments = IntStream
                .range(1,6)
                .mapToObj(id -> PaymentDto.builder().id(Integer.toString(id)).build())
                .collect(Collectors.toList());

        this.paymentFilter = new PaymentFilter("1");
    }

    @Test
    public void getRetrieveWithUserId() throws Exception {
        when(paymentService
                .retrievePaymentByUserId(anyString(), any(PageRequest.class), any(HttpServletResponse.class)))
                .thenReturn(payments);

        final ResultActions result = this.mockMvc
                .perform(get( "/payment/retrieve?userId=1"))
                .andExpect(status().isOk());

        for(int i = 0; i < payments.size(); i++) {
            result.andExpect((jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId())));
        }

        final PageRequest pageRequest = PageUtil.createPageRequest();
        verify(paymentService).retrievePaymentByUserId(
                eq("1"), eq(pageRequest), any(HttpServletResponse.class)
        );
    }

    @Test
    public void postRetrieveWithFilter() throws Exception {
        when(paymentService.retrieve(any(PaymentFilter.class), any(PageRequest.class), any(HttpServletResponse.class)))
                .thenReturn(payments);

        final String jsonResult = MAPPER.writeValueAsString(paymentFilter);

        final ResultActions result = this.mockMvc
                .perform(post("/payment/retrieve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResult)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for(int i = 0; i < payments.size(); i++) {
            result.andExpect((jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId())));
        }

        final PageRequest pageRequest = PageUtil.createPageRequest();
        verify(paymentService).retrieve(eq(paymentFilter), eq(pageRequest), any(HttpServletResponse.class));
    }

    @Test
    public void retrieveWithUserIdMissing() throws Exception {
        this.mockMvc.perform(get("/payment/retrieve")).andExpect(status().isBadRequest());
    }

    @Test
    public void retrieveWithEmptyUserId() throws Exception {
        this.mockMvc.perform(get("/payment/retrieve?userId=")).andExpect(status().isBadRequest());
    }
}
