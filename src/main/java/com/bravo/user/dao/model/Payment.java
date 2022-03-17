package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "payment")
public class Payment {

  @Id
  @Column(name = "id")
  @GenericGenerator(name = "uuid", strategy = "uuid4")
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
  private LocalDateTime updated = LocalDateTime.now();

}
