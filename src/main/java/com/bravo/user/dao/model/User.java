package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bravo.user.enumerator.Role;
import com.bravo.user.model.dto.UserSaveDto;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "email", nullable = false)
  private String email;
  
  @Column(name = "password", length=60, nullable = false)
  private String password;
  
  @Enumerated(EnumType.STRING)
  private Role role;
  
  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public User(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }

  public User(final UserSaveDto user){
    this();
    this.firstName = user.getFirstName();
    this.middleName = user.getMiddleName();
    this.lastName = user.getLastName();
    this.phoneNumber = user.getPhoneNumber();
    this.email = user.getEmail();
    this.role = Role.findRole(user.getRole());
    this.password = user.getPassword();
  }
}
