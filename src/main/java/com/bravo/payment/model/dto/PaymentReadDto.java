package com.bravo.payment.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaymentReadDto {

  private String id;
  private String cardNumberLast4;
  private Integer expiryMonth;
  private Integer expiryYear;
  private LocalDateTime updated;
}
