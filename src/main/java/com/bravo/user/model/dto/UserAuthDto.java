package com.bravo.user.model.dto;

import com.bravo.user.utility.AuthUtil;
import com.bravo.user.utility.AuthProvider;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the information necessary to authenticate the user credentials.
 * 
 * @author Daryl Boggs
 */
@Data
@NoArgsConstructor
public class UserAuthDto implements AuthProvider {

	private String email;
	private char[] password;

	@Override
	public void clearAuth() {
		AuthUtil.clearAuth(password);
	}

}
