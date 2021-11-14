package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.AuthProvider;
import com.bravo.user.utility.AuthUtil;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User implements AuthProvider {

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
  
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "sec_pw_hash", nullable = false)
	private byte[] hash;

	@Column(name = "sec_pw_salt", nullable = false)
	private byte[] salt;

	@Column(name = "user_role_id", nullable = true)
	private Integer userRoleID;

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
	setEmail(user.getEmail());
	salt = AuthUtil.generateSalt();
	hash = AuthUtil.hashPassword(user.getPassword(), salt);
	if (user.getUserRole() != null) {
		userRoleID = user.getUserRole().getUserRoleID();
	}
  }

	/**
	 * The database is not case sensitive, so here we make sure was are making all
	 * the characters lowercase.
	 * 
	 * @param email String
	 */
	public void setEmail(String email) {
		if (StringUtils.hasText(email)) {
			this.email = email.toLowerCase();
		}
	}

	@Override
	public void clearAuth() {
		AuthUtil.clearAuth(salt);
		AuthUtil.clearAuth(hash);
	}
}
