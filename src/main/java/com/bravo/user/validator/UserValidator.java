package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.ValidatorUtil;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidator extends CrudValidator {

  public void validateId(String id){
    if(ValidatorUtil.isInvalid(id)){
      throw new BadRequestException("'id' is required");
    }
  }
  
  /**
   * Validates a user name to ensure it is not null, not empty, and does not contain space, numbers, or special characters.
   * 
   * @param name User name to validate
   * @throws BadRequestException if the name is invalid
   */
  public void validateName(String name) {
	  Pattern pattern = Pattern.compile("[^a-z*%!]", Pattern.CASE_INSENSITIVE);
	  if (ValidatorUtil.isInvalid(name) || pattern.matcher(name).find()) {
		  throw new BadRequestException("'name' is required and should not contain space, numbers, or special characters"
		  		+ "other than '*', '%', and '!'");
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
  }

  @Override
  protected void validateUpdate(Object o, Errors errors) {

    UserSaveDto instance = (UserSaveDto) o;

    if(ValidatorUtil.isEmpty(instance, "id", "updated")){
      errors.reject("'request' modifiable field(s) are required");
    }
  }
}
