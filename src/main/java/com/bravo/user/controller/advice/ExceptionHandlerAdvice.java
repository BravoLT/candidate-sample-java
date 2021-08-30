package com.bravo.user.controller.advice;

import com.bravo.user.model.dto.AuthErrorDto;
import com.bravo.user.model.dto.ErrorDto;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
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

  /**
   * Handles an unauthorized exception.
   *
   * @param exception authentication exception to be handled
   */
  @ExceptionHandler(AuthenticationException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public AuthErrorDto handleAuthenticationException(final AuthenticationException exception) {
    log.error("An unauthorized authentication exception occurred ", exception);
    return new AuthErrorDto(exception.getClass(), exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }
}
