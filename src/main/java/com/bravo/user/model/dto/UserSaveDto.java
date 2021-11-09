package com.bravo.user.model.dto;

import com.bravo.user.dao.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserSaveDto {

  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private String userRole;

}
