package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.AddressDto;
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

  public <T extends Collection<Address>> List<AddressDto> convertAddresses(final T addresses){
    return addresses.stream().map(this::convertAddress).collect(Collectors.toList());
  }

  public AddressDto convertAddress(final Address address){
    final AddressDto dto = mapperFacade.map(address, AddressDto.class);
    dto.setAddress(
        String.format("%s%s %s, %s, %s",
            address.getLine1(),
            address.getLine2() != null ? String.format(" %s", address.getLine2()) : "",
            address.getCity(),
            address.getState(),
            address.getZip()
        )
    );
    return dto;
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
    return dto;
  }
}
