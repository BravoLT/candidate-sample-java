package com.bravo.user.model.dto;

/**
 * Represents the role of the user in the system.
 * 
 * @author Daryl Boggs
 */
public enum UserRole {
	ADMIN(1);

	private Integer userRoleID;
	private String name;

	private UserRole(Integer userRoleID) {
		this.userRoleID = userRoleID;
	}

	/**
	 * Retrieves the UserRole for a given ID.
	 * 
	 * @param userRoleID Integer
	 * @return {@link UserRole}
	 */
	public static UserRole getUserRoleByID(Integer userRoleID) {
		UserRole userRole = null;
		for (UserRole currentUserRole : UserRole.values()) {
			if (currentUserRole.getUserRoleID().equals(userRoleID)) {
				userRole = currentUserRole;
				break;
			}
		}
		return userRole;
	}

	public Integer getUserRoleID() {
		return userRoleID;
	}

	public String getName() {
		return name;
	}

}
