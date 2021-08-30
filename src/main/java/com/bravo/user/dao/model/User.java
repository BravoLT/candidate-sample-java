package com.bravo.user.dao.model;

import com.bravo.user.model.dto.UserSaveDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "user")
public class User {

  @Id
  @Column(name = "id")
  private String id;

  @OneToOne(mappedBy = "user")
  private Auth auth;

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
  }
}
