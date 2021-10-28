package com.bravo.user.dao.converters;

import com.bravo.user.enumerator.Role;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.stream.Stream;

@Component
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {


    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }
        return Stream.of(Role.values())
                .filter(c -> c.name().equals(role))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
