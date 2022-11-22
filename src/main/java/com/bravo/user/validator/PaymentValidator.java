package com.bravo.user.validator;

import org.springframework.stereotype.Component;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.utility.ValidatorUtil;

/*
 * The Payment Validator
 * 
 * @author Bob Wilson
 * Created: November 2022
 */
@Component
public class PaymentValidator extends CrudValidator {

	/*
	 * Validate the ID.
	 * 
	 * @param id
	 */
	public void validateId(String id) {
		if (ValidatorUtil.isInvalid(id)) {
			throw new BadRequestException("'id' is required");
		}
	}

	/*
	 * Validate the userId.
	 * 
	 * @param userId
	 */
	public void validateUserId(String userId) {
		if (ValidatorUtil.isInvalid(userId)) {
			throw new BadRequestException("'userId' is required");
		}
		if (!userId.matches("^[A-Za-z]+$")) {
			throw new BadRequestException("'userId' cannot contain numbers, spaces, or special characters");
		}
	}

}
