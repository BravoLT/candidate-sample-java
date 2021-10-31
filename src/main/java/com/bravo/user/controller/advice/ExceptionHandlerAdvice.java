package com.bravo.user.controller.advice;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.ErrorDto;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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


  @ExceptionHandler(value = DataNotFoundException.class)
  public ResponseEntity<ErrorDto> handleDataNotFoundException(DataNotFoundException e) {
    return getErrorDtoResponseEntity(e, HttpStatus.NOT_FOUND, DataNotFoundException.class);
  }

  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException e) {
    return getErrorDtoResponseEntity(e, HttpStatus.BAD_REQUEST, BadRequestException.class);
  }

  private ResponseEntity<ErrorDto> getErrorDtoResponseEntity(RuntimeException e, HttpStatus status, Class<?> exception) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setStatusCode(status.value());
    errorDto.setMessage(e.getMessage());
    errorDto.setException(exception);
    errorDto.setErrors(Set.of(e.getMessage().split("\\|")[1].trim()));
    return ResponseEntity.status(status).body(errorDto);
  }
}
