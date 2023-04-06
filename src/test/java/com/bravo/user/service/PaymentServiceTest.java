package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.specification.UserNameFuzzySpecification;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.filter.UserNameFuzzyFilter;
import com.bravo.user.utility.PageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    private List<PaymentDto> paymentDtos;

    private final String USER_ID = "008a4215-0b1d-445e-b655-a964039cbb5a";

    @BeforeEach
    public void beforeEach(){
        final List<Integer> ids = IntStream
                .range(1, 10)
                .boxed()
                .collect(Collectors.toList());

        final Page<Payment> mockPage = mock(Page.class);
        when(paymentRepository.findAllByUserId(anyString(), any(PageRequest.class))).thenReturn(mockPage);

        final Payment mockPaymentOne = Mockito.mock(Payment.class);
        final Payment mockPaymentTwo = Mockito.mock(Payment.class);
        final List<Payment> mockPayments = Arrays.asList(mockPaymentOne, mockPaymentTwo);
        when(mockPage.getContent()).thenReturn(mockPayments);

        PaymentDto mockPaymentDtoOne = Mockito.mock(PaymentDto.class);
        PaymentDto mockPaymentDtoTwo = Mockito.mock(PaymentDto.class);
        this.paymentDtos = Arrays.asList(mockPaymentDtoOne, mockPaymentDtoTwo);
        when(resourceMapper.convertPayments(mockPayments)).thenReturn(paymentDtos);
    }

    @Test
    public void retrieveByUserId() {
        final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
        final List<PaymentDto> results = paymentService.retrieveByUserId(USER_ID, pageRequest, httpResponse);
        assertEquals(this.paymentDtos, results);
        assertEquals("0", httpResponse.getHeader("page-count"));
        assertEquals("1", httpResponse.getHeader("page-number"));
        assertEquals("20", httpResponse.getHeader("page-size"));

        verify(paymentRepository).findAllByUserId(USER_ID, pageRequest);
    }

    @Test
    public void retrieveByUserIdPaged() {
        final PageRequest pageRequest = PageUtil.createPageRequest(2, 5);
        final List<PaymentDto> results = paymentService.retrieveByUserId(USER_ID, pageRequest, httpResponse);
        assertEquals(this.paymentDtos, results);
        assertEquals("0", httpResponse.getHeader("page-count"));
        assertEquals("2", httpResponse.getHeader("page-number"));
        assertEquals("5", httpResponse.getHeader("page-size"));

        verify(paymentRepository).findAllByUserId(USER_ID, pageRequest);
    }


}
