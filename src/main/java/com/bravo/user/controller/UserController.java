package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.enumerator.Crud;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.validator.UserValidator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
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
    return userService.create(request);
  }

  @GetMapping(value = "/retrieveId/{id}")
  @ResponseBody
  public UserReadDto retrieveById(@PathVariable String id){
    return userService.retrieveById(id);
  }
  
  /**
   * Retrieves a paginated list of users whose first, middle, and last names match the specified name.
   * The name to search may contain negation ("!") or wildcard ("*" or "%") characters
   * 
   * @param name Name to find - should contain first, middle, and last name without space
   * @param page Page number to retrieve
   * @param size Size of the page to retrieve
   * @param response {@linkplain HttpServletResponse}
   * @return List of {@linkplain UserReadDto}s with combined first, middle, and last names that match the search criteria
   */
  @GetMapping(value = "/retrieveName/{name}")
  @ResponseBody
  public List<UserReadDto> retrieveByName(
	  final @PathVariable String name,
	  final @RequestParam(required = false) Integer page,
	  final @RequestParam(required = false) Integer size,
	  final HttpServletResponse response) {
	  
	  userValidator.validateName(name);
	  final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
	  return userService.retrieveByName(name, pageRequest, response);
  }

  @PostMapping(value = "/retrieve")
  @ResponseBody
  public List<UserReadDto> retrieve(
      final @RequestBody UserFilter filter,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ){
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
  public boolean delete(final @PathVariable String id){
    userValidator.validateId(id);
    return userService.delete(id);
  }
}
