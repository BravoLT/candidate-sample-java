package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.filter.PaymentFilter;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

    private final UserValidator userValidator;
    private final PaymentService paymentService;
    private static final Logger LOGGER =
            LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(
            UserValidator userValidator,
            PaymentService paymentService
    ){
        this.userValidator = userValidator;
        this.paymentService = paymentService;
    }

    /*
        Included a Get for direct retrieve by UserId, and
        a Post with a filter for possible future expansion
        of the code, making it more robust and easy to
        add to the code down the line. E. Erwin
     */
    @GetMapping(value = "/retrieve")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @RequestParam(required = false) String userId,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse
    ){
        if(userId != null) {
            userValidator.validateId(userId);
            final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
            return paymentService.retrievePaymentByUserId(userId, pageRequest, httpResponse);
        } else {
            throw new BadRequestException("'userId' is required!");
        }
    }
    @PostMapping(value = "/retrieve")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @RequestBody PaymentFilter filter,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse
    ){
        LOGGER.info("Request to retrieve payment information being conducted...");
        final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
        return paymentService.retrieve(filter, pageRequest, httpResponse);
    }

}
