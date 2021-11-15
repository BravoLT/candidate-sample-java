package com.bravo.user.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.bravo.user.utility.DateUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {

    // The DateUil utility class has no methods nor even a default constructor,
    // so all we can really test is if it's properties, which are specifically
    // DateTimeFormatter objects.
    // Really, this just ends up being tests for the DateTimeFormatter.format()
    // method.

    @Test
    void hasCorrectDateFormat() {
        DateTimeFormatter testDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals(DATE_FORMAT.format(LocalDate.now()), testDateFormat.format(LocalDate.now()));
    }

    @Test
    void hasCorrectDateTimeFormat() {
        DateTimeFormatter testDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertEquals(DATE_TIME_FORMAT.format(LocalDateTime.now()), testDateTimeFormat.format(LocalDateTime.now()));
    }

    @Test
    void hasCorrectTimeFormat() {
        DateTimeFormatter testTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        assertEquals(TIME_FORMAT.format(LocalTime.now()), testTimeFormat.format(LocalTime.now()));
    }
}