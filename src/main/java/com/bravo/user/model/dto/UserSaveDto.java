package com.bravo.user.model.dto;

import com.bravo.user.enumerator.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class UserSaveDto extends UserLoginDto{

  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
  private Role role;
}
