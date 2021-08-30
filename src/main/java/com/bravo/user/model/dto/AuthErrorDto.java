package com.bravo.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * DTO for an auth error
 */
@Data
@AllArgsConstructor
public class AuthErrorDto {

    private Class<?> exception;
    private String message;
    private HttpStatus status;
}
