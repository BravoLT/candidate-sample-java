/* (C)2021 */
package com.bravo.user.model.dto;

import com.bravo.user.enumerator.Role;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserReadDto {

    private String id;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime updated;
}
