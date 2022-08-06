package com.bravo.user.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDto {

  private String id;
  private String cardNumberLast4;
  private Integer expiryMonth;
  private Integer expiryYear;
  private LocalDateTime updated;
}
