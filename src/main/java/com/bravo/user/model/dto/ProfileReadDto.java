package com.bravo.user.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProfileReadDto {

  private String id;
  private String imageRef;
  private LocalDateTime updated;
}
