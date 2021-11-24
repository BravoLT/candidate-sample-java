package com.bravo.user.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PasswordValidateDto {

  private String email;
  private String password;
}
