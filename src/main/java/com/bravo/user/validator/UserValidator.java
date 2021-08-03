package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.ValidatorUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import com.bravo.user.model.filter.UserFilter;

@Component
public class UserValidator extends CrudValidator {

  public void validateId(String id){
    if(ValidatorUtil.isInvalid(id)){
      throw new BadRequestException("'id' is required");
    }
  }

  @Override
  protected void validateCreate(Object o, Errors errors) {

    UserSaveDto instance = (UserSaveDto) o;

    /* Admittedly, this is a bit of a hack storing the phone number length in
     * the UserFilter class and having to new up an instance here :(
     */
    UserFilter userFilter = new UserFilter();

    // required fields
    if(ValidatorUtil.isInvalid(instance.getFirstName())){
      errors.reject("'firstName' is required");
    }
    if(ValidatorUtil.isInvalid(instance.getLastName())){
      errors.reject("'lastName' is required");
    }
    if(ValidatorUtil.isInvalid(instance.getPhoneNumber())){
      errors.reject("'phoneNumber' is required and must contain only digits");
    }
    if(ValidatorUtil.isInvalidLength(instance.getPhoneNumber(), userFilter.getPhoneNumberLength())){
      errors.reject("'phoneNumber' must be 10 digits in length");
    }

  }

  @Override
  protected void validateUpdate(Object o, Errors errors) {

    UserSaveDto instance = (UserSaveDto) o;

    if(ValidatorUtil.isEmpty(instance, "id", "updated")){
      errors.reject("'request' modifiable field(s) are required");
    }

    if(String.valueOf(instance.getPhoneNumber()).length() > 0 && ValidatorUtil.isInvalid(instance.getPhoneNumber())){
      errors.reject("'phoneNumber' is must contain only digits");
    }
  }
}
