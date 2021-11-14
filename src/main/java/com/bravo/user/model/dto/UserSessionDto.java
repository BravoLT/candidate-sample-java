package com.bravo.user.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the session information returned once the User is authenticated.
 * 
 * @author Daryl Boggs
 */
@Data
@NoArgsConstructor
public class UserSessionDto {

	private String token;
	private UserReadDto user;

}
