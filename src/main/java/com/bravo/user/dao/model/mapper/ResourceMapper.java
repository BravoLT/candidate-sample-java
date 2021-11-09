package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.Profile;
import com.bravo.user.model.dto.ProfileReadDto;
import com.bravo.user.model.dto.UserReadDto;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

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
    UserReadDto dto = mapperFacade.map(user, UserReadDto.class);

    String name;
    if(user.getMiddleName() != null && !user.getMiddleName().trim().isEmpty()){
      name = String.format("%s %s %s", user.getFirstName(), user.getMiddleName(), user.getLastName());
    } else {
      name = String.format("%s %s", user.getFirstName(), user.getLastName());
    }
    dto.setName(name);
    return dto;
  }

  public ProfileReadDto convertProfile(final Profile profile) {
    return mapperFacade.map(profile, ProfileReadDto.class);
  }
}
