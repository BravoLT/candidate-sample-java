/* (C)2021 */
package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.utility.ValidatorUtil;
import java.util.Objects;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidator extends CrudValidator {

    public void validateId(String id) {
        if (ValidatorUtil.isInvalid(id)) {
            throw new BadRequestException("'id' is required");
        }
    }

    public void validateName(String name) {
        if (ValidatorUtil.isInvalid(name)) {
            throw new BadRequestException("'name' is required");
        }
        if (!name.matches("^[A-Za-z]+$")) {
            throw new BadRequestException(
                    "'name' cannot contain numbers, spaces, or special characters");
        }
    }

    public void validateEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new BadRequestException("'email' is required");
        }
    }

    public void validatePassword(String password) {
        if (ValidatorUtil.isInvalid(password)) {
            throw new BadRequestException("'Password' is required");
        }
    }

    @Override
    protected void validateCreate(Object o, Errors errors) {

        UserSaveDto instance = (UserSaveDto) o;

        // required fields
        if (ValidatorUtil.isInvalid(instance.getFirstName())) {
            errors.reject("'firstName' is required");
        }
        if (ValidatorUtil.isInvalid(instance.getLastName())) {
            errors.reject("'lastName' is required");
        }

        if (Objects.isNull(instance.getPhoneNumber())
                || !instance.getPhoneNumber().matches("[0-9]{10}")) {
            errors.reject("'phoneNumber' of format [0-9]{10} is required");
        }
    }

    @Override
    protected void validateUpdate(Object o, Errors errors) {

        UserSaveDto instance = (UserSaveDto) o;

        if (ValidatorUtil.isEmpty(instance, "id", "updated")) {
            errors.reject("'request' modifiable field(s) are required");
        }

        if (Objects.nonNull(instance.getPhoneNumber())
                && !instance.getPhoneNumber().matches("[0-9]{10}")) {
            errors.reject("'phoneNumber' of format [0-9]{10} is required");
        }
    }
}
