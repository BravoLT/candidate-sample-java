package com.bravo.user.dao.model;

import com.bravo.user.dao.converters.ColumnEncryptor;
import com.bravo.user.enumerator.Role;
import com.bravo.user.model.dto.UserSaveDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

  @Id
  @Column(name = "id")
  private String id;

  @NotNull
  @Column(name = "email", unique = true)
  private String email;

  @NotNull
  @Column(name = "password")
  @Convert(converter = ColumnEncryptor.class)
  private String password;

  @Column(name = "role")
  private Role role;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

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
    this.password = user.getPassword();
    this.role = user.getRole();
  }
}
