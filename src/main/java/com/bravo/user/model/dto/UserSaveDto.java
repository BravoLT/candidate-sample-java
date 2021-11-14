package com.bravo.user.model.dto;

import com.bravo.user.utility.AuthProvider;
import com.bravo.user.utility.AuthUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSaveDto implements AuthProvider {

  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
	private String email;
	private char[] password;
	private UserRole userRole;

	@Override
	public void clearAuth() {
		AuthUtil.clearAuth(password);
	}

}
