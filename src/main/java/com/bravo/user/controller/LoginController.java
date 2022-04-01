package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.model.dto.LoginDto;
import com.bravo.user.service.LoginService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/login")
//also the first time I have seen/used Swagger but it looks like its a tool to help you
//build consumer and document RESTful APIs
//I have worked with Postman only in the past but they seem similar?
@SwaggerController
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }
  //a post request means we will be adding information to our database
  @PostMapping
  public void login(final @RequestBody LoginDto request, HttpServletResponse httpResponse){
    if(request.getUsername() == null){
      throw new IllegalArgumentException("'username' is required");
    }
    if(request.getPassword() == null){
      throw new IllegalArgumentException("'password' is required");
    }
    loginService.login(request);
    httpResponse.setStatus(204);
  }
}
