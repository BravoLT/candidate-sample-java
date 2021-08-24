package com.bravo.user.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.AddressReadDto;
import com.bravo.user.utility.ValidatorUtil;

@Component
public class AddressValidator extends CrudValidator {

  public void validateId(String id){
    if(ValidatorUtil.isInvalid(id)){
      throw new BadRequestException("'id' is required");
    }
  }

  @Override
  protected void validateRetrieve(Object o, Errors errors) {

	AddressReadDto instance = (AddressReadDto) o;

    // required fields
    if(ValidatorUtil.isInvalid(instance.getAddressLine1())){
      errors.reject("'addressLine1' is required");
    }
    if(ValidatorUtil.isInvalid(instance.getZip())){
      errors.reject("'zip' is required");
    }

  }

}
