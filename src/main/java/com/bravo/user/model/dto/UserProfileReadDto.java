package com.bravo.user.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserProfileReadDto {

  private String id;
  private String userId;
  private String imageRef;
  private LocalDateTime updated;
}
