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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {

    private static final String PAGE_COUNT = "page-count";
    private static final String PAGE_NUMBER = "page-number";
    private static final String PAGE_SIZE = "page-size";
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
        final List<Integer> ids = List.of(1, 2, 3, 4, 5, 6);

        this.paymentFilter =  PaymentFilter.builder().userId("1").build();

        this.paymentDtos = ids.stream()
                .map(id -> PaymentDto.builder().id(Integer.toString(id)).build())
                .collect(Collectors.toList());
        when(resourceMapper.convertPayments(anyList())).thenReturn(paymentDtos);

        final List<Payment> payments = ids.stream()
                .map(id -> new Payment(id.toString()))
                .collect(Collectors.toList());

        final Page<Payment> mockPage = mock(Page.class);
        when(mockPage.getContent()).thenReturn(payments);
        when(mockPage.getTotalPages()).thenReturn(6);

        when(paymentRepository.findAll(any(PaymentSpecification.class), any(PageRequest.class)))
                .thenReturn(mockPage);
    }

    @Test
    public void retrievePaymentByUserId() {
        final String userId = "1";
        final PageRequest pageRequest = PageUtil.createPageRequest();
        final List<PaymentDto> results = paymentService.retrievePaymentByUserId(userId, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader(PAGE_COUNT));
        assertEquals("1", httpResponse.getHeader(PAGE_NUMBER));
        assertEquals("20", httpResponse.getHeader(PAGE_SIZE));

        final PaymentFilter filter = PaymentFilter.builder().userId("1").build();
        final PaymentSpecification specification = new PaymentSpecification(filter);
        verify(paymentRepository).findAll(specification, pageRequest);
    }

    @Test
    public void retrievePaymentByUserIdWithPagination() {
        final String userId = "1";
        final PageRequest pageRequest = PageUtil.createPageRequest(1, 6);
        final List<PaymentDto> results = paymentService.retrievePaymentByUserId(userId, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader(PAGE_COUNT));
        assertEquals("1", httpResponse.getHeader(PAGE_NUMBER));
        assertEquals("6", httpResponse.getHeader(PAGE_SIZE));

        final PaymentFilter filter = PaymentFilter.builder().userId(userId).build();
        final PaymentSpecification specification = new PaymentSpecification(filter);
        verify(paymentRepository).findAll(specification, pageRequest);
    }

    @Test
    public void retrieveWithFilter() {
        final PageRequest pageRequest = PageUtil.createPageRequest();
        final List<PaymentDto> results = paymentService.retrieve(paymentFilter, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader(PAGE_COUNT));
        assertEquals("1", httpResponse.getHeader(PAGE_NUMBER));
        assertEquals("20", httpResponse.getHeader(PAGE_SIZE));

        final PaymentSpecification specification = new PaymentSpecification(paymentFilter);
        verify(paymentRepository).findAll(specification, pageRequest);
    }

    @Test
    public void retrieveWithFilterWithPagination() {
        final PageRequest pageRequest = PageUtil.createPageRequest(1, 6);
        final List<PaymentDto> results = paymentService.retrieve(paymentFilter, pageRequest, httpResponse);
        assertEquals(paymentDtos, results);
        assertEquals("6", httpResponse.getHeader(PAGE_COUNT));
        assertEquals("1", httpResponse.getHeader(PAGE_NUMBER));
        assertEquals("6", httpResponse.getHeader(PAGE_SIZE));

        final PaymentSpecification specification = new PaymentSpecification(paymentFilter);
        verify(paymentRepository).findAll(specification, pageRequest);
    }
}
