package com.bravo.user.exception;

import java.text.MessageFormat;

public class PaymentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Payment not found for user with id {0}";

    public PaymentNotFoundException(String userId) {
        super(MessageFormat.format(MESSAGE, userId));
    }
}
