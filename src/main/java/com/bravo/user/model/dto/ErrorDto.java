package com.bravo.user.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorDto {

  private Set<String> errors;
  private Class<?> exception;
  private String message;
  private Integer statusCode;

}
