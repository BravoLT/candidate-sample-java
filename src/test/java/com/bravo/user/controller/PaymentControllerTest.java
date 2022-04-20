package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
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
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private final UserValidator userValidator = new UserValidator();

    private List<PaymentDto> payments;

    @BeforeEach
    public void beforeEach() {
        this.payments = createPaymentsDto();
    }

    @Test
    void getRetrieveWithUserId() throws Exception {
        when(paymentService.retrieveByUserId(anyString())).thenReturn(payments);

        final ResultActions result = this.mockMvc
                .perform(get("/payment/retrieve?userId=" + "userId"))
                .andExpect(status().isOk());

        for (int i = 0; i < 2; i++) {
            result.andExpect(jsonPath(String.format("$[%d].id", i)).value(payments.get(i).getId()));
            result.andExpect(jsonPath(String.format("$[%d].cardNumberLast4", i)).value(payments.get(i).getCardNumberLast4()));
        }

        verify(paymentService).retrieveByUserId(eq("userId"));
    }

    @Test
    void getRetrieveWithIdEmpty() throws Exception {
        this.mockMvc.perform(get("/payment/retrieve?userId="))
                .andExpect(status().isBadRequest());
    }

    private List<PaymentDto> createPaymentsDto() {

        List<PaymentDto> payments = new ArrayList<>();

        final PaymentDto payment1 = new PaymentDto();
        payment1.setId("1234");
        payment1.setCardNumberLast4("1111");
        payment1.setExpiryYear(2026);
        payment1.setExpiryMonth(4);
        payment1.setUpdated(LocalDateTime.now());

        final PaymentDto payment2 = new PaymentDto();
        payment2.setId("5678");
        payment2.setCardNumberLast4("2222");
        payment2.setExpiryYear(2036);
        payment2.setExpiryMonth(6);
        payment2.setUpdated(LocalDateTime.now());

        payments.add(payment1);
        payments.add(payment2);

        return payments;
    }
}