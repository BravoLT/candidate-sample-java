package com.bravo.user.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.dto.UserAuthDto;

/**
 * Tests for user authentication utilities.
 * 
 * @author Daryl Boggs
 */
class AuthUtilTest {

	private static final int SALT_AND_HASH_BYTE_LENGTH = 16;

	/**
	 * Verify generateSalt produces a sixteen byte array, and that the result is
	 * different every time.
	 */
	@Test
	public void generateSalt() {
		byte[] saltOne = AuthUtil.generateSalt();
		byte[] saltTwo = AuthUtil.generateSalt();

		assertNotNull(saltOne);
		assertNotNull(saltTwo);
		assertEquals(SALT_AND_HASH_BYTE_LENGTH, saltOne.length);
		assertEquals(SALT_AND_HASH_BYTE_LENGTH, saltTwo.length);
		assertFalse(Arrays.equals(saltOne, saltTwo));
	}

	/**
	 * Verify hashPassword produces a sixteen byte array, and that the result is the
	 * same every time.
	 */
	@Test
	public void hashPassword() {
		char[] password = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };
		byte[] salt = AuthUtil.generateSalt();
		byte[] hashOne = AuthUtil.hashPassword(password, salt);
		byte[] hashTwo = AuthUtil.hashPassword(password, salt);

		assertNotNull(hashOne);
		assertNotNull(hashTwo);
		assertEquals(SALT_AND_HASH_BYTE_LENGTH, hashOne.length);
		assertEquals(SALT_AND_HASH_BYTE_LENGTH, hashTwo.length);
		assertTrue(Arrays.equals(hashOne, hashTwo));
		assertFalse(Arrays.equals(hashOne, salt));
		assertFalse(Arrays.equals(hashTwo, salt));
	}

	/**
	 * Verify clearAuth is writing over primitive char and byte arrays with all
	 * spaces.
	 */
	@Test
	public void clearAuth() {
		char[] passwordOne = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };
		char[] passwordTwo = passwordOne.clone();

		UserAuthDto userSignInDto = new UserAuthDto();
		userSignInDto.setPassword(passwordOne);
		UserSaveDto userSaveDto = new UserSaveDto();
		userSaveDto.setPassword(passwordTwo);
		User user = new User(userSaveDto);

		userSignInDto.clearAuth();
		userSaveDto.clearAuth();
		user.clearAuth();

		for (int index = 0; index < passwordOne.length; index++) {
			assertEquals(' ', passwordOne[index]);
			assertEquals(' ', userSignInDto.getPassword()[index]);
			assertEquals(' ', passwordTwo[index]);
			assertEquals(' ', userSaveDto.getPassword()[index]);
		}

		for (int index = 0; index < SALT_AND_HASH_BYTE_LENGTH; index++) {
			assertEquals(' ', user.getSalt()[index]);
			assertEquals(' ', user.getHash()[index]);
		}

	}

}