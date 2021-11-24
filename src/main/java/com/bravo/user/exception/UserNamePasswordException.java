package com.bravo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNamePasswordException extends RuntimeException {

  public UserNamePasswordException(final String message){
    super(String.format("UserName/Passworod Invalid | %s", message));
  }
}
