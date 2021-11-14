package com.bravo.user.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.AuthUtil;
import com.bravo.user.utility.ValidatorUtil;

@Component
public class UserValidator extends CrudValidator {

  public void validateId(String id){
    if(ValidatorUtil.isInvalid(id)){
      throw new BadRequestException("'id' is required");
    }
  }

  public void validateName(String name){
    if(ValidatorUtil.isInvalid(name)){
      throw new BadRequestException("'name' is required");
    }
    if(!name.matches("^[A-Za-z]+$")){
      throw new BadRequestException("'name' cannot contain numbers, spaces, or special characters");
    }

  }

	/**
	 * Validates that valid e-mail address and password were provided. A valid
	 * e-mail address can never be longer than a total of 254 characters. A password
	 * with less than 8 characters is weak. A password with more than 64 characters
	 * will trigger pre-hashing, which has certain security and performance problems
	 * if abused.<br/>
	 * <br/>
	 * References: <br/>
	 * https://www.regular-expressions.info/email.html <br/>
	 * https://blog.greglow.com/2019/05/17/sql-column-length-storing-email-addresses-sqlserver-database/
	 * <br/>
	 * https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#pbkdf2
	 * 
	 * @param email    String
	 * @param password char{@literal []}
	 */
	public void validateEmailAndPassword(String email, char[] password) {
		if (ValidatorUtil.isInvalid(email) || email.length() > 254
				|| !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$") || password == null
				|| password.length < 8 || password.length > 64) {
			AuthUtil.clearAuth(password);
			throw new BadRequestException(
					"'email' and 'password' are required; 'email' must contain alphanumeric text, followed an '@', followed by a domain, and 'password' must be at least 8 characters, but no more than 64 characters.");
		}
	}

  @Override
  protected void validateCreate(Object o, Errors errors) {

    UserSaveDto instance = (UserSaveDto) o;

    // required fields
    if(ValidatorUtil.isInvalid(instance.getFirstName())){
      errors.reject("'firstName' is required");
    }
    if(ValidatorUtil.isInvalid(instance.getLastName())){
      errors.reject("'lastName' is required");
    }

    if(Objects.isNull(instance.getPhoneNumber()) || !instance.getPhoneNumber().matches("[0-9]{10}")){
      errors.reject("'phoneNumber' of format [0-9]{10} is required");
    }
  }

  @Override
  protected void validateUpdate(Object o, Errors errors) {

    UserSaveDto instance = (UserSaveDto) o;

    if(ValidatorUtil.isEmpty(instance, "id", "updated")){
      errors.reject("'request' modifiable field(s) are required");
    }

    if(Objects.nonNull(instance.getPhoneNumber()) && !instance.getPhoneNumber().matches("[0-9]{10}")){
      errors.reject("'phoneNumber' of format [0-9]{10} is required");
    }
  }
}
