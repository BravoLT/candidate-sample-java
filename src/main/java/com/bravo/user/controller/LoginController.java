package com.bravo.user.controller;


import com.bravo.user.model.dto.LoginDto;
import com.bravo.user.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Login", description = "Login for Users")
@RequestMapping(value = "/login")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @Operation(summary = "Login as a User")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = void.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
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
