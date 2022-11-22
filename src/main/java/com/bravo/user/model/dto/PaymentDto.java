package com.bravo.user.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Payment data transfer object
 * 
 * @author Bob Wilson
 * Created November 2022
 * 
 */
@Data
public class PaymentDto {

  private String id;
  private String userId;
  private String cardNumberLast4;
  private Integer expiryMonth;
  private Integer expiryYear;
  private LocalDateTime updated;

}
