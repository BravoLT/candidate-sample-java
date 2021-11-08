package com.bravo.user.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSaveDto {

  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
  private UserProfileDto profile;
}
