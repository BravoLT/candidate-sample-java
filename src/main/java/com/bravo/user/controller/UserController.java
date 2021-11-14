package com.bravo.user.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.enumerator.Crud;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.exception.UnauthorizedException;
import com.bravo.user.model.dto.UserAuthDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.dto.UserSessionDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;
import com.bravo.user.validator.UserValidator;

@RequestMapping(value = "/user")
@SwaggerController
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;

  public UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  @PostMapping(value = "/create")
  @ResponseBody
  public UserReadDto create(final @RequestBody UserSaveDto request, final BindingResult errors)
      throws BindException {
		userValidator.validateEmailAndPassword(request.getEmail(), request.getPassword());
    userValidator.validate(Crud.CREATE, request, errors);
    return userService.create(request);
  }

  @GetMapping(value = "/retrieve")
  @ResponseBody
  public List<UserReadDto> retrieve(
      final @RequestParam(required = false) String id,
      final @RequestParam(required = false) String name,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    if(id != null){
      userValidator.validateId(id);
      return Collections.singletonList(userService.retrieve(id));
    }
    else if(name != null){
      userValidator.validateName(ValidatorUtil.removeControlCharacters(name));
      final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
      return userService.retrieveByName(name, pageRequest, httpResponse);
    }
    else {
      throw new BadRequestException("'id' or 'name' is required!");
    }
  }

  @PostMapping(value = "/retrieve")
  @ResponseBody
  public List<UserReadDto> retrieve(
      final @RequestBody UserFilter filter,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
    return userService.retrieve(filter, pageRequest, httpResponse);
  }

  @PatchMapping(value = "/update/{id}")
  @ResponseBody
  public UserReadDto update(
      final @PathVariable String id,
      final @RequestBody UserSaveDto request,
      final BindingResult errors
  ) throws BindException {
    userValidator.validateId(id);
    userValidator.validate(Crud.UPDATE, request, errors);
    return userService.update(id, request);
  }

  @DeleteMapping(value = "/delete/{id}")
  @ResponseBody
  public boolean delete(final @PathVariable String id) {
    userValidator.validateId(id);
    return userService.delete(id);
  }

	/**
	 * Authenticates the user. Returns 200 with a User Session DTO containing user
	 * data and a placeholder for a session token. Returns 401 Unauthorized if the
	 * credentials are invalid or don't match our records. <br/>
	 * <br/>
	 * References: <br/>
	 * https://datatracker.ietf.org/doc/html/rfc7235#section-3.1 <br/>
	 * https://www.freecodecamp.org/news/http-401-error-vs-http-403-error-status-code-responses-explained/
	 * 
	 * @param request {@link UserAuthDto}
	 * @throws BindException
	 */
	@PostMapping(value = "/authenticate")
	@ResponseBody
	public ResponseEntity<UserSessionDto> authenticate(final @RequestBody UserAuthDto request) throws BindException {
		UserSessionDto response = new UserSessionDto();
		try {
			userValidator.validateEmailAndPassword(request.getEmail(), request.getPassword());
		} catch (BadRequestException badRequestException) {
			throw new UnauthorizedException();
		}
		response.setUser(userService.authenticateUser(request));
		response.setToken("placeholder, not implemented");
		return ResponseEntity.ok(response);
	}

}
