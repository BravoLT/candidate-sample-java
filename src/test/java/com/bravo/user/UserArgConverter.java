package com.bravo.user;

import com.bravo.user.dao.model.Auth;
import com.bravo.user.dao.model.User;
import com.bravo.user.enumerator.Role;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Helper converter class used to build a user entity from a csv file
 */
public class UserArgConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
        String[] userProp = o.toString().split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        var auth = new Auth(
                userProp[7],
                userProp[8],
                null,
                Role.valueOf(userProp[9]),
                null
        );
        return new User(
                userProp[0],
                auth,
                userProp[2],
                userProp[3],
                userProp[4],
                userProp[5],
                LocalDateTime.parse(userProp[6], formatter)
        );
    }
}