package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "profile")
public class Profile {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "picture_url")
  private String pictureUrl;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Profile(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }
}
