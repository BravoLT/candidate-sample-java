package com.bravo.user.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserReadDto {

  private String id;
  private String name;
  private Integer phoneNumber;
  private LocalDateTime updated;

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNumber(Integer phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

}
