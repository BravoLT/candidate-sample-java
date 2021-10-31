package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.enumerator.Crud;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.PasswordDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;
import com.bravo.user.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RequestMapping(value = "/user")
@AllArgsConstructor
@SwaggerController
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;


  @PostMapping(value = "/create")
  @ResponseBody
  public UserReadDto create(final @RequestBody UserSaveDto request, final BindingResult errors)
      throws BindException {
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


  @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody  ResponseEntity<UserReadDto> validatePassword(final @RequestBody PasswordDto passwordDto) {
    return ResponseEntity.ok(userService.validatePassword(passwordDto));
  }

}
