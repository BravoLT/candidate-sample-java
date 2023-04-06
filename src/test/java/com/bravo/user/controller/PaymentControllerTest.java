package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void retrievePaymentWithUserIdAndNoPagination() throws Exception {
        PaymentDto mockPaymentDtoOne = Mockito.mock(PaymentDto.class);
        PaymentDto mockPaymentDtoTwo = Mockito.mock(PaymentDto.class);
        List<PaymentDto> mockPaymentDtos = Arrays.asList(mockPaymentDtoOne, mockPaymentDtoTwo);
        when(paymentService.retrieveByUserId(anyString(), isNull(), isA(HttpServletResponse.class)))
                .thenReturn(mockPaymentDtos);

        String userId = "008a4215-0b1d-445e-b655-a964039cbb5a";
        final ResultActions result = this.mockMvc
                .perform(get("/payment/retrieve/" + userId))
                .andExpect(status().isOk());

        verify(paymentService).retrieveByUserId(eq(userId), any(PageRequest.class), any(HttpServletResponse.class));
    }

}
