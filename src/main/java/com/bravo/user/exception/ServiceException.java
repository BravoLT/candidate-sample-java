package com.bravo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents an Internal Server Error as an Exception.
 * 
 * @author Daryl Boggs
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		this("Unknown error occured.");
	}

	public ServiceException(final String message) {
		super(String.format("Internal Server Error | %s", message));
	}

}
