package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.model.dto.UserSaveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private UserSaveDto userSaveDto;

    @Test
   void getUserPayments() {

        List<Payment> testPayments = new ArrayList<>();
        testPayments.add(new Payment());
        testPayments.add(new Payment());
        //Set up a New User
        User user = new User();
        user.setPayments(testPayments);  // Set payments into User's Payment List
        System.out.println(paymentService.getUserPayments(user.getId()));

    }
}