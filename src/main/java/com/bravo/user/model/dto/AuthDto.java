package com.bravo.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for an auth login
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    private String email;
    private String password;
}
