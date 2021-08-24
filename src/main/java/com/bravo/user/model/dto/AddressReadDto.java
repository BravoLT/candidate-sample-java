package com.bravo.user.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AddressReadDto {

  private String id;
  private String userId;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String state;
  private String zip;
  
  private LocalDateTime updated;
}
