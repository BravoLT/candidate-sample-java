package com.bravo.user.dao.model;

import com.bravo.user.dao.model.secure.PasswordEncryptor;
import com.bravo.user.model.dto.UserSaveDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

  public enum Role {
    ADMIN
  }

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

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  @Convert(converter = PasswordEncryptor.class)
  private String password;

  @Column(name = "role", nullable = true)
  @Enumerated(EnumType.STRING)
  private Role role;

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
    this.role = "ADMIN".equals(user.getRole()) ? User.Role.ADMIN : null;
  }
}
