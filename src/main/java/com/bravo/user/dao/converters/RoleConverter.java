/* (C)2021 */
package com.bravo.user.dao.converters;

import com.bravo.user.enumerator.Role;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

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
