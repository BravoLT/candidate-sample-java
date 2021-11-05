package com.bravo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Unauthorized exception that is thrown when a user validation fails for
 * whatever reason <br>
 * This exception made the most sense to me at this time, I am sure there are
 * other valid ones to consider
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(final String message) {
    super(String.format("UnauthorizedException | %s", message));
  }

}
