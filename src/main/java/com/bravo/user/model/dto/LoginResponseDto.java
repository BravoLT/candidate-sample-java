package com.bravo.user.model.dto;

import com.bravo.user.dao.model.User;
import lombok.Data;

@Data
public class LoginResponseDto {

    private Boolean isSuccess = Boolean.FALSE;
    private UserReadDto user;
    private String errorMessage;

    public static LoginResponseDto fail(String errorMessage){
        LoginResponseDto response = new LoginResponseDto();
        response.setIsSuccess(Boolean.FALSE);
        response.setErrorMessage(errorMessage);

        return response;
    }

}
