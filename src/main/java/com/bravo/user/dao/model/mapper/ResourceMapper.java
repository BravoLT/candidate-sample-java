package com.bravo.user.dao.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserRole;

import ma.glasnost.orika.MapperFacade;

@Component
public class ResourceMapper {

  private final MapperFacade mapperFacade;

  public ResourceMapper(MapperFacade mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  public <T extends Collection<User>> List<UserReadDto> convertUsers(final T users){
    return users.stream().map(this::convertUser).collect(Collectors.toList());
  }

  public UserReadDto convertUser(final User user){
    final UserReadDto dto = mapperFacade.map(user, UserReadDto.class);

    String name;
    if(user.getMiddleName() != null && !user.getMiddleName().trim().isEmpty()){
      name = String.format("%s %s %s", user.getFirstName(), user.getMiddleName(), user.getLastName());
    } else {
      name = String.format("%s %s", user.getFirstName(), user.getLastName());
    }
    dto.setName(name);

	UserRole userRole = UserRole.getUserRoleByID(user.getUserRoleID());
	dto.setUserRole(userRole);

    return dto;
  }
}
