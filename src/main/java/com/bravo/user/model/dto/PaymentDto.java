package com.bravo.user.model.dto;

import java.time.LocalDateTime;

import com.bravo.user.dao.model.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {

  private String id;
  /*added this to make sure the payload is returning the correct user*/
  private String userId;
  private String cardNumberLast4;
  private Integer expiryMonth;
  private Integer expiryYear;
  private LocalDateTime updated;

  public PaymentDto(final String id) {
    this();
    this.id = id;
  }

}
