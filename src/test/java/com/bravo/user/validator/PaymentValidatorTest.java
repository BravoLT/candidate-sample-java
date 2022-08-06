package com.bravo.user.validator;

import com.bravo.user.App;
import com.bravo.user.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentValidatorTest {

  private PaymentValidator paymentValidator = mock(PaymentValidator.class);

  @Test
  public void testPaymentValidationSuccess() {
    paymentValidator.validateId(UUID.randomUUID().toString());
    verify(paymentValidator, times(1)).validateId(anyString());
  }

  @ParameterizedTest
  @ValueSource(strings = {"Huh", "What?!", "WDF?", "0x00002342342526"})
  public void testPaymentValidatorBadRequestException(String userId) {

    doThrow(new BadRequestException(PaymentValidator.USER_ID_MISSING_ERROR))
            .when(paymentValidator).validateId(anyString());

    Exception exception = assertThrows(BadRequestException.class, () -> {
      paymentValidator.validateId(userId);
    });

    String expectedMessage = PaymentValidator.USER_ID_MISSING_ERROR;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}