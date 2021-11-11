package com.bravo.user.model.dto;

import lombok.Data;

/**
 * A simple data transfer object to allow the transmission of
 * email and password for validation.
 */
@Data
public class PasswordValidateDto {

  private String email;
  private String password;

}
