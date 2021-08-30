package com.bravo.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for an auth token
 */
@Data
@AllArgsConstructor
public class AuthTokenDto {

    private String accessType;
    private String token;
}
