package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "line1", nullable = false)
  private String line1;

  @Column(name = "line2")
  private String line2;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "state", nullable = false)
  private String state;

  @Column(name = "zip", nullable = false)
  private String zip;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Address(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }
}
