package com.bravo.user.controller;


import com.bravo.user.dao.model.User;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.model.dto.LoginRequestDto;
import com.bravo.user.model.dto.LoginResponseDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.utility.EncryptDecryptUtil;
import com.bravo.user.utility.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin

@RequestMapping("/login")

public class LoginApi {

    private final UserRepository userRepository;


    LoginApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws Exception {
        LoginResponseDto response = new LoginResponseDto();
        EncryptDecryptUtil encryptDecryptUtil = new EncryptDecryptUtil();
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null || !SecurityUtil.verifyPassword(encryptDecryptUtil.encrypt(request.getPassword()), user.getPassword())) {
            response.setIsSuccess(Boolean.FALSE);
            response.setErrorMessage("Incorrect Email Address or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        UserReadDto u = covert(user);
        response.setIsSuccess(Boolean.TRUE);
        response.setUser(u);

        return ResponseEntity.ok(response);
    }

    private UserReadDto covert(User user){

        UserReadDto u = new UserReadDto();
        u.setEmail(user.getEmail());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setMiddleName(user.getMiddleName());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setId(user.getId());
        u.setUserRole(user.getUserRole());

        return u;
    }


}
