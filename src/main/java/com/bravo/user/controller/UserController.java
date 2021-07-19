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
import java.util.Set;

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
  
  /**
   * Retrieves a single user by ID.
   * 
   * @param id ID to find
   * @return {@linkplain UserReadDto} with matching ID, if it exists
   */
  @GetMapping(value = "/retrieve/{id}")
  @ResponseBody
  public UserReadDto retrieve(@PathVariable String id){
    return userService.retrieve(id);
  }

  /**
   * Retrieves a paginated list of users by name and/or ID. If the name is specified, it should contain combined 
   * first, middle, and last names to match. The name to search may also contain negation ("!") and/or wildcard
   * ("*" or "%") characters
   * 
   * @param id User ID to find (optional) 
   * @param name User name to find (optional). Should contain first, middle, and last name without space
   * @param page Page number to retrieve
   * @param size Size of the page to retrieve
   * @param response {@linkplain HttpServletResponse} in which to store page headers
   * @return List of {@linkplain UserReadDto}s with properties that meet the search criteria
   */
  @GetMapping(value = "/retrieve")
  @ResponseBody
  public List<UserReadDto> retrieve(
	  final @RequestParam(name = "id", required = false) String id,
	  final @RequestParam(name = "name", required = false) String name,
	  final @RequestParam(required = false) Integer page,
	  final @RequestParam(required = false) Integer size,
	  final HttpServletResponse response) {
	  
	  final UserFilter filter = new UserFilter();
	  if (id != null) {
		  userValidator.validateId(id);
		  filter.setIds(Set.of(id));
	  }
	  if (name != null) {
		  userValidator.validateName(name);
		  filter.setFullNames(Set.of(name));
	  }
	  return retrieve(filter, page, size, response);
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
