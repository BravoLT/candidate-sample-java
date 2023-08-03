package com.bravo.user.controller;

import com.bravo.user.enumerator.Crud;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;
import com.bravo.user.validator.UserValidator;
import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="User", description="User Actions")
@RequestMapping(value = "/user")
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;

  public UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  @Operation(summary = "Create a User")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = UserReadDto.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
  @PostMapping(value = "/create")
  @ResponseBody
  public UserReadDto create(final @RequestBody UserSaveDto request, final BindingResult errors)
      throws BindException {
    userValidator.validate(Crud.CREATE, request, errors);
    return userService.create(request);
  }

  @Operation(summary = "Retrieve User Information")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
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

  @Operation(summary = "Retrieve User Information")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
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

  @Operation(summary = "Update User Information")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = UserReadDto.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
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

  @Operation(summary = "Delete a user")
  @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = boolean.class), mediaType = "application/json")
  })
  @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
  @DeleteMapping(value = "/delete/{id}")
  @ResponseBody
  public boolean delete(final @PathVariable String id) {
    userValidator.validateId(id);
    return userService.delete(id);
  }
}
