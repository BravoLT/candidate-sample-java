package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.service.PaymentService;
import com.bravo.user.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void retrievePaymentsByUser() throws Exception {

        final String userId = "user-id";

        when(paymentService.retrieveByUserId(userId)).thenReturn(TestUtil.getPaymentsDto());

        final MvcResult result = this.mockMvc
                .perform(get("/payment/retrieve/" + userId))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertNotNull(result);
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("\"id\" : \"15040055-a640-4cea-cfb7-357669fcdefe\""));

    }

    @Test
    void retrievePaymentsByUserEmptyPayments() throws Exception {

        final String userId = "user-id";

        when(paymentService.retrieveByUserId(userId)).thenReturn(Collections.emptyList());

        final MvcResult result = this.mockMvc
                .perform(get("/payment/retrieve/" + userId))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(responseBody, "[ ]");
    }

    @Test
    void retrievePaymentsByUserNotFound() throws Exception {
        this.mockMvc.perform(get("/payment/retrieve/")).andExpect(status().isNotFound());
    }
}