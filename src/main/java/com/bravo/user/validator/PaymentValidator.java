package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.utility.ValidatorUtil;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator extends CrudValidator {

  public void validateId(String id){
    if(ValidatorUtil.isInvalid(id)){
      throw new BadRequestException("'User Id' is required");
    }
  }
}
