package com.bravo.user.service;

import com.bravo.user.App;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.dao.specification.PaymentSpecification;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.filter.PaymentFilter;
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
    private List<PaymentDto> paymentDtos;
    private PaymentFilter paymentFilter;


    @BeforeEach
    public void beforeEach() {
        final List<Integer> ids = IntStream
                .range(1,6)
                .boxed()
                .collect(Collectors.toList());

        this.paymentFilter = createPaymentFilter("1");

        final List<Payment> payments = ids.stream()
                .map(id -> createPayment(id.toString()))
                .collect(Collectors.toList());
        this.paymentDtos = ids.stream()
                .map(id -> createPaymentDto(id.toString()))
                .collect(Collectors.toList());
        when(resourceMapper.convertPayments(payments)).thenReturn(paymentDtos);

        final Page<Payment> mockPage = mock(Page.class);
        when(paymentRepository.findAll(any(PaymentSpecification.class), any(PageRequest.class)))
                .thenReturn(mockPage);

        when(mockPage.getContent()).thenReturn(payments);
        when(mockPage.getTotalPages()).thenReturn(6);


    }



    @Test
    public void testRetrievePaymentByUserId()
    {
        final String userId = "1";
        final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
        final List<PaymentDto> results = paymentService.retrievePaymentByUserId(userId, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader("page-count"));
        assertEquals("1", httpResponse.getHeader("page-number"));
        assertEquals("20", httpResponse.getHeader("page-size"));

        final PaymentFilter filter = new PaymentFilter(userId);
        final PaymentSpecification specification = new PaymentSpecification(filter);
        verify(paymentRepository).findAll(specification, pageRequest);
    }

    @Test
    public void testRetrievePaymentByUserIdWithPagination()
    {
        final String userId = "1";
        final PageRequest pageRequest = PageUtil.createPageRequest(1, 6);
        final List<PaymentDto> results = paymentService.retrievePaymentByUserId(userId, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader("page-count"));
        assertEquals("1", httpResponse.getHeader("page-number"));
        assertEquals("6", httpResponse.getHeader("page-size"));

        final PaymentFilter filter = new PaymentFilter(userId);
        final PaymentSpecification specification = new PaymentSpecification(filter);
        verify(paymentRepository).findAll(specification, pageRequest);
    }

    @Test
    public void testRetrieveWithFilter()
    {
        final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
        final List<PaymentDto> results = paymentService.retrieve(paymentFilter, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader("page-count"));
        assertEquals("1", httpResponse.getHeader("page-number"));
        assertEquals("20", httpResponse.getHeader("page-size"));

        final PaymentSpecification specification = new PaymentSpecification(paymentFilter);
        verify(paymentRepository).findAll(specification, pageRequest);

    }

    @Test
    public void testRetrieveWithFilterWithPagination()
    {
        final PageRequest pageRequest = PageUtil.createPageRequest(1, 6);
        final List<PaymentDto> results = paymentService.retrieve(paymentFilter, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader("page-count"));
        assertEquals("1", httpResponse.getHeader("page-number"));
        assertEquals("6", httpResponse.getHeader("page-size"));

        final PaymentSpecification specification = new PaymentSpecification(paymentFilter);
        verify(paymentRepository).findAll(specification, pageRequest);

    }

    private PaymentFilter createPaymentFilter(String userId) {

        final PaymentFilter paymentFilter = new PaymentFilter();
        paymentFilter.setUserId(userId);
        return paymentFilter;
    }

    private Payment createPayment(String paymentId)
    {
        final Payment payment = new Payment();
        payment.setId(paymentId);
        return payment;
    }

    private PaymentDto createPaymentDto(String paymentDtoId)
    {
        final PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(paymentDtoId);
        return paymentDto;
    }
}
