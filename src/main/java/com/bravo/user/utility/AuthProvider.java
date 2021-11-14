package com.bravo.user.utility;

/**
 * Interface for any model containing user auth credentials.
 * 
 * @author Daryl Boggs
 */
public interface AuthProvider {

	/**
	 * Run this when we are done to clear password, salt, and hash where applicable
	 * from memory.
	 */
	public void clearAuth();

}
