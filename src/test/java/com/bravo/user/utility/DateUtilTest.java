package com.bravo.user.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtilTest {
   //expected objects

    public void test_date_time_formatter_date_only_format() {

        String expectedOutcome = "2022-04-01";
        Date testDate = new Date(4-1-22);
        DateTimeFormatter expectedFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //i'm not entirely sure how to proceed with this but my thought process is to write a test
        //that makes sure the output of DateTimeFormatter is the pattern specified
        //Assertions.assertEquals(expectedOutcome, expectedFormatterDate.format(testDate));

    }



    DateTimeFormatter expectedFormatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter expectedFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");



}
