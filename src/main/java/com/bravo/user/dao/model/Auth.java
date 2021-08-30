package com.bravo.user.dao.model;

import com.bravo.user.enumerator.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth")
public class Auth {

  @Id
  @Column
  private String id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column
  private Role role;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
  private User user;
}
