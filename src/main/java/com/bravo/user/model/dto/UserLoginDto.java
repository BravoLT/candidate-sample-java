/* (C)2021 */
package com.bravo.user.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {
    private String email;
    private String password;
}
