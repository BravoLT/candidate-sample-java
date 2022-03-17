package com.bravo.user.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
  private String id;
  private String cardNumber;
  private Integer expiryMonth;
  private Integer expiryYear;
  private LocalDateTime updated;
}
