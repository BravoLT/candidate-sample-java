package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.service.PaymentService;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/retrieve/{userId}")
    @ResponseBody
    public List<PaymentDto> retrieve(
            final @PathVariable String userId,
            final @RequestParam(required = false) Integer page,
            final @RequestParam(required = false) Integer size,
            final HttpServletResponse httpResponse
    ) {
            final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
            return paymentService.retrieveByUserId(userId, pageRequest, httpResponse);

    }

}
