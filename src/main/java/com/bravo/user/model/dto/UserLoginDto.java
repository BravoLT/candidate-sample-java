package com.bravo.user.model.dto;

import com.bravo.user.enumerator.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto{
  private String email;
  private String password;
}
