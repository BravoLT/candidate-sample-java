package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.dao.model.User;
import com.bravo.user.enumerator.Crud;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.exception.ForbiddenException;
import com.bravo.user.model.dto.PasswordValidateDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;
import com.bravo.user.validator.UserValidator;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    userValidator.validate(Crud.CREATE, request, errors);
    try {
      return userService.create(request);
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException("Missing required fields, or field was invalid.");
    }
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

  @PostMapping(value = "/validate")
  public ResponseEntity<Void> validatePassword(final @RequestBody PasswordValidateDto credentials) {

    if (credentials == null) {
      throw new ForbiddenException("Access is forbidden.");
    }

    String email = StringUtils.trimWhitespace(credentials.getEmail());
    String password = credentials.getPassword();

    if (!StringUtils.hasLength(email) || !StringUtils.hasLength(password)) {
      throw new ForbiddenException("Access is forbidden.");
    }

    try {
      User user = userService.retrieveByEmail(email);
      if (!Objects.equals(user.getPassword(), password)) {
        throw new ForbiddenException("Access is forbidden.");
      }
    } catch (DataNotFoundException e) {
      throw new ForbiddenException("Access is forbidden.");
    }

    return ResponseEntity.ok().build();
  }
}
