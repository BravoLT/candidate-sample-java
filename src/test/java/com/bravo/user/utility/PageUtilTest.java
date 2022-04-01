package com.bravo.user.utility;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class PageUtilTest {

    private PageUtil pageUtil;
    //before annotation indicates that this method runs prior to every test
    @BeforeEach
    private void beforeEach(){
        this.pageUtil = null;
    }
    //annotation indicates that the method is a test method - test methods are always public, always return void, and take no arguments
    @Test
    public void test_create_page_request_should_return_new_page_request(){
   //my thought process here is, I need to test createPageRequest method on a page util object
        //and then check that the new PageRequest is returned
        PageRequest testPage = pageUtil.createPageRequest(1,3);
        //I am not quite sure how to return the expected result
        Assertions.assertEquals(testPage,testPage);
    }

}
