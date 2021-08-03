package com.bravo.user.model.dto;

import lombok.Data;

@Data
public class UserSaveDto {

  private String firstName;
  private String middleName;
  private String lastName;
  private Integer phoneNumber;

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public Integer getPhoneNumber() {
    return phoneNumber;
  }


}
