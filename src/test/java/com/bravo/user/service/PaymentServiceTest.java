package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private HttpServletResponse httpResponse;

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private ResourceMapper resourceMapper;

    @MockBean
    private PaymentRepository paymentRepository;

    private List<PaymentDto> dtoPayments;

    @BeforeEach
    public void beforeEach() {
        final List<Integer> ids = IntStream
                .range(1, 10)
                .boxed()
                .collect(Collectors.toList());

        final Page<Payment> mockPage = mock(Page.class);
        when(paymentRepository.findByUserId(any(String.class), any(PageRequest.class)))
                .thenReturn(mockPage);

        final List<Payment> daoPayments = ids.stream()
                .map(id -> createPayment(Integer.toString(id)))
                .collect(Collectors.toList());
        when(mockPage.getContent()).thenReturn(daoPayments);
        when(mockPage.getTotalPages()).thenReturn(9);

        this.dtoPayments = ids.stream()
                .map(id -> createPaymentDto(Integer.toString(id)))
                .collect(Collectors.toList());
        when(resourceMapper.convertPayments(daoPayments)).thenReturn(dtoPayments);
    }

    @Test
    public void retrieveByName() {
        final String input = "input";
        final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
        final List<PaymentDto> results = paymentService.retrieveByUserId(input, pageRequest, httpResponse);
        assertEquals(dtoPayments, results);
        assertEquals("9", httpResponse.getHeader("page-count"));
        assertEquals("1", httpResponse.getHeader("page-number"));
        assertEquals("20", httpResponse.getHeader("page-size"));

        verify(paymentRepository).findByUserId(input, pageRequest);
    }

    private PaymentDto createPaymentDto(final String id) {
        final PaymentDto payment = new PaymentDto();
        payment.setId(id);
        payment.setCardNumberLast4("0000");
        return payment;
    }

    private Payment createPayment(final String id) {
        final Payment payment = new Payment();
        payment.setId(id);
        payment.setCardNumber("5400000000000000");
        return payment;
    }
}
