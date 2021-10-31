package com.bravo.user.dao.model;

import com.bravo.user.model.dto.UserRole;
import com.bravo.user.model.dto.UserSaveDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

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

  @Column(name = "email", nullable = false, unique = true, columnDefinition = "varchar(255) default ''")
  private String email;

  @Column(name = "password", nullable = false , columnDefinition = "varchar(255) default ''")
  private String password;

  @Column(name = "role", nullable = false, columnDefinition = "int default 1")
  private UserRole userRole;

  @Column(name = "updated", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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
    this.userRole = user.getRole();
  }
}
