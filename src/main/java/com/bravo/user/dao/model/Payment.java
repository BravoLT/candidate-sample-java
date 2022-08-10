package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "payment")
public class Payment {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "card_number", nullable = false)
  private String cardNumber;

  @Column(name = "expiry_month", nullable = false)
  private Integer expiryMonth;

  @Column(name = "expiry_year", nullable = false)
  private Integer expiryYear;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Payment(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }
}
