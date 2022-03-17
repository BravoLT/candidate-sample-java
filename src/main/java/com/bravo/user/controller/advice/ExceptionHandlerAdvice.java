package com.bravo.user.controller.advice;

import com.bravo.user.exception.PaymentNotFoundException;
import com.bravo.user.model.dto.ErrorDto;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

  @ExceptionHandler(value = BindException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorDto handleBindException(final BindException exception){

    final Set<String> errors = exception
        .getAllErrors().stream()
        .filter(e -> e.getCodes() != null && e.getCodes().length > 1)
        .map(e -> e.getCodes()[1])
        .collect(Collectors.toSet());

    final ErrorDto response = new ErrorDto();
    response.setErrors(errors);
    response.setException(exception.getClass());
    response.setMessage("BadRequest: try to resolve the 'errors' in your request");
    response.setStatusCode(400);
    return response;
  }

  @ExceptionHandler(value = PaymentNotFoundException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorDto handlePaymentNotFoundException(final PaymentNotFoundException exception){
    log.info("Payments not found, details: {}", exception.getMessage());
    var errorDto = new ErrorDto();
    errorDto.setMessage(exception.getMessage());
    errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());

    return errorDto;
  }

}
