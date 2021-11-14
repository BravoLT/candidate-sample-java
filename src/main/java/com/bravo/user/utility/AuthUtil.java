package com.bravo.user.utility;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.bravo.user.exception.ServiceException;

/**
 * A collection of helper methods for user authentication.
 * 
 * @author Daryl Boggs
 */
public class AuthUtil {

	/**
	 * According to OWASP, SecureRandom is best for generating the Salt. According
	 * to the article below SecureRandom.getInstanceStrong() doesn't have much
	 * benefit over the default constructor, and using it could cause threads to
	 * become blocked if using Docker or another VM due to very low entropy.
	 * 
	 * References:
	 * https://cheatsheetseries.owasp.org/cheatsheets/Cryptographic_Storage_Cheat_Sheet.html#secure-random-number-generation<br/>
	 * https://tersesystems.com/blog/2015/12/17/the-right-way-to-use-securerandom/
	 * 
	 * @return byte{@literal []}
	 */
	public static byte[] generateSalt() {
		byte[] salt = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		return salt;
	}

	/**
	 * Hashes the password using PBKDF2, the encryption algorithm recommended by
	 * NIST. PBKDF2WithHmacSHA256 is supported as of Java 8.<br/>
	 * <br/>
	 * References:<br/>
	 * https://www.baeldung.com/java-password-hashing <br/>
	 * https://en.wikipedia.org/wiki/PBKDF2 <br/>
	 * https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#pbkdf2
	 * <br/>
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SunJCEProvider
	 * 
	 * @param password char{@literal []}
	 * @param salt     byte{@literal []}
	 * @return byte{@literal []}
	 */
	public static byte[] hashPassword(char[] password, byte[] salt) {
		byte[] hash = null;
		try {
			KeySpec keySpec = new PBEKeySpec(password, salt, 310000, 128);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			throw new ServiceException(exception.getMessage());
		}
		return hash;
	}

	/**
	 * As an added layer of security, clear the password from memory by writing over
	 * the char array with spaces. This is why we store the password as a primitive
	 * char array.
	 * 
	 * @param password char{@literal []}
	 */
	public static void clearAuth(char[] password) {
		if (password != null && password.length != 0) {
			Arrays.fill(password, ' ');
		}
	}

	/**
	 * As an added layer of security, clear the hash from memory by writing over the
	 * byte array with spaces. This is why we store the hash as a primitive byte
	 * array.
	 * 
	 * @param hash byte{@literal []}
	 */
	public static void clearAuth(byte[] hash) {
		if (hash != null && hash.length != 0) {
			Arrays.fill(hash, (byte) ' ');
		}
	}

}
