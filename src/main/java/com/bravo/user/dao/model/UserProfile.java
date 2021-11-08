package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "user_profile")
public class UserProfile {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "image_ref", nullable = false)
  private String imageRef;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public UserProfile() {
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }

}
