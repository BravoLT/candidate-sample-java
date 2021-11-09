package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bravo.user.model.dto.ProfileSaveDto;

import lombok.Data;

@Entity
@Data
@Table(name = "profile")
public class Profile {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "image_ref", nullable = false)
  private String imageRef;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Profile() {
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }

  public Profile(final ProfileSaveDto dto) {
    this();
    this.imageRef = dto.getImageRef();
  }

}
