package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.ValidatorUtil;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PaymentValidator extends CrudValidator {

    public void validateUserId(String id){
        if(ValidatorUtil.isInvalid(id)){
            throw new BadRequestException("'id' is required");
        }
    }


}
