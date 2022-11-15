package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

import lombok.Data;

@Entity
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }
}
