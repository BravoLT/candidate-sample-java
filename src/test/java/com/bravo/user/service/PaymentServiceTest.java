package com.bravo.user.service;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private ResourceMapper resourceMapper;


    @Test
   void getUserPayments() {

        List<Payment> testPayments = new ArrayList<>();
        testPayments.add(new Payment());
        testPayments.add(new Payment());
        //Set up a New User
        User user = userRepository.save(new User(new UserSaveDto()));
        user.setPayments(testPayments);

        assertEquals(2, paymentService.getUserPayments(user.getId()).size());


    }
}