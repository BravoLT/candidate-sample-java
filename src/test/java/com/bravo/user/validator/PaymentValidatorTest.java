package com.bravo.user.validator;

import com.bravo.user.App;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentValidatorTest {

  @Autowired
  private PaymentValidator paymentValidator;

  @Test
  public void testPaymentValidatorSuccess() {

  }
}