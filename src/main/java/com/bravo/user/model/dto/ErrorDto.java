package com.bravo.user.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class ErrorDto {

  private Set<String> errors;
  private Class<?> exception;
  private String message;
  private Integer statusCode;
}
