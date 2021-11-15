package com.bravo.user.utility;

import com.bravo.user.model.filter.DateFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorUtilTest {

    Vector<String> testInstance1 = new Vector<String>();
    DateFilter<LocalDateTime> testInstance2 = new DateFilter<>();
    String testInstance3 = "this is a string";
    Integer testInstance4 = 4;

    @BeforeEach
    void setUp() {
        testInstance1.add("one");
        testInstance1.add("two");
        testInstance2.setStart(LocalDateTime.now());
        testInstance2.setUntil(LocalDateTime.MAX);
    }

    @Test
    void validatorUtilIsValidGoesThroughFirstBranchForCollections() {
        assertTrue(ValidatorUtil.isValid(testInstance1));
    }

    @Test
    void validatorUtilIsValidGoesThroughSecondBranchForDateFilter() {
        assertTrue(ValidatorUtil.isValid(testInstance2));
    }

    @Test
    void validatorUtilIsValidGoesThroughThirdBranchForString() {
        assertTrue(ValidatorUtil.isValid(testInstance3));
    }

    @Test
    void validatorUtilIsValidGoesThroughSecondBranchForNotNull() {
        assertTrue(ValidatorUtil.isValid(testInstance4));
    }

    @Test
    void validatorUtilValueIsInvalid() {
        assertFalse(ValidatorUtil.isValid(null));
        assertTrue(ValidatorUtil.isInvalid(null));
    }

    @Test
    void validatorUtilValueIsEmptyWithEmptyObject() {
        assertTrue(ValidatorUtil.isEmpty(new Object(), "these", "aren't", "real"));
    }

    @Test
    void validatorUtilControlCharactersAreRemovedFromString() {
        assertEquals("hello", ValidatorUtil.removeControlCharacters("he%l!l*o"));
    }
}