package com.bravo.user.utility;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PageUtilTest {

    @Test
    void pageUtilCreatePageRequestReturnsAPageOfZeroWhenNegativeIntegerPassed() {
        Integer testPageInt = -1;
        PageRequest testPR = PageRequest.of(0, 1);

        assertEquals(testPR, PageUtil.createPageRequest(testPageInt, 1));
    }

    @Test
    void pageUtilCreatePageRequestUsesDefaultSizeValueWhenSizeIsLessThanZero() {
        Integer testSize = -1;
        PageRequest testPR = PageRequest.of(0, 1);

        assertEquals(testPR, PageUtil.createPageRequest(1, testSize, 1));;
    }

    @Test
    void pageUtilCreatePageRequestCreateANextPageIterationWhenIntroduced () {
        PageRequest testPR = PageRequest.of(0, 1);

        assertEquals(PageUtil.createPageRequest(1, 1), testPR.previous());
    }

    @Test
    void pageUtilUpdatePageHeadersIsCalledOnce() {
        PageUtil mockPageUtil = mock(PageUtil.class);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        List<String> list = new ArrayList<>();
        list.add("Page 1");
        list.add("Page 2");
        Page<String> testPage = new PageImpl<>(list);
        PageRequest testPR = PageRequest.of(0, 1);

        verify(mockPageUtil, times(1)).updatePageHeaders(mockHttpServletResponse, testPage, testPR);
    }

    // Testing void methods of a final class is a pain
    //@Test
    //void pageUtilUpdatePageHeadersCallsSetIntHeaderThreeTimes() {
    //    MockHttpServletResponse mockHttpServletResponse = mock(MockHttpServletResponse.class);
    //    ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
    //    ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
    //    PageUtil mockPageUtil = mock(PageUtil.class);
    //    List<String> list = new ArrayList<>();
    //    list.add("Page 1");
    //    list.add("Page 2");
    //    Page<String> testPage = new PageImpl<>(list);
    //    PageRequest testPR = PageRequest.of(0, 1);
    //    // Errors out here with a UnfinishedStubbingException
    //    // thinks we need to add a .thenReturn when our method is void
    //    doNothing().when(mockPageUtil).updatePageHeaders(mockHttpServletResponse, testPage, testPR);
    //    //mockPageUtil.updatePageHeaders(mockHttpServletResponse, testPage, testPR);
    //    verify(mockHttpServletResponse, times(3)).setIntHeader(stringCaptor.capture(), intCaptor.capture());
   //}
}