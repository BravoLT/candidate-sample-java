package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.service.PaymentService;
import com.bravo.user.service.UserService;
import com.bravo.user.validator.UserValidator;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SwaggerController
@RequestMapping(value = "/payments")
public class PaymentController{

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/retrieve/{userId}/")
    @ResponseBody
    public List<Payment> getUserPayments(@PathVariable String userId) {
        userValidator.validateId(userId);
        return paymentService.getUserPayments(userId);
    }



}
