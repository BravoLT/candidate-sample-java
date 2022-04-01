package com.bravo.user.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorUtilTest {
    //creating test object
    private ValidatorUtil validatorUtil;
    //reset our test object before each test
    @BeforeEach
    public void beforeEach(){
        this.validatorUtil = null;
    }

    @Test
    public void isEmpty(){
        boolean testValue = ValidatorUtil.isEmpty("cat");

    }

}
