package com.bravo.user.controller.advice;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bravo.user.model.dto.ErrorDto;

@ControllerAdvice
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
  
  @ExceptionHandler(value = ConstraintViolationException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public ErrorDto handleSqlConflict(final ConstraintViolationException exception) {
      final ErrorDto response = new ErrorDto();
      response.setErrors(Stream.of(exception.getConstraintName()).collect(Collectors.toSet()));
      response.setException(exception.getClass());
      response.setMessage("Ensure you're attempting to create a unique user");
      response.setStatusCode(409);
      return response;
  }
}
