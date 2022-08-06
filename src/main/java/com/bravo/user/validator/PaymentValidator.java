package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.utility.ValidatorUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PaymentValidator extends CrudValidator {
  private static final String USER_ID_MISSING_ERROR = "'User Id' in UUID format is required";

  public void validateId(String id){
    if(!ValidatorUtil.isUUIDValid(id)){
      throw new BadRequestException(USER_ID_MISSING_ERROR);
    }
  }

  @Override
  protected void validateRetrieve(Object o, Errors errors) {
    String userId = (String)o;
    if(!ValidatorUtil.isUUIDValid(userId)){
      throw new BadRequestException(USER_ID_MISSING_ERROR);
    }
  }
}
