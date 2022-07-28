package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.Profile;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.dto.ProfileDto;
import com.bravo.user.model.dto.UserReadDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

  public <T extends Collection<Payment>> List<PaymentDto> convertPayments(final T payments){
    return payments.stream().map(this::convertPayment).collect(Collectors.toList());
  }

  public PaymentDto convertPayment(final Payment payment){
    final String cardNumber = payment.getCardNumber();
    final PaymentDto dto = mapperFacade.map(payment, PaymentDto.class);
    dto.setCardNumberLast4(cardNumber.substring(cardNumber.length() - 4));
    return dto;
  }

  public ProfileDto convertProfile(final Profile profile){
    return mapperFacade.map(profile, ProfileDto.class);
  }

  public <T extends Collection<User>> List<UserReadDto> convertUsers(final T users){
    return users.stream().map(this::convertUser).collect(Collectors.toList());
  }

  public UserReadDto convertUser(final User user){
    final UserReadDto dto = mapperFacade.map(user, UserReadDto.class);
    dto.setName(
        String.format("%s%s %s",
            user.getFirstName(),
            user.getMiddleName() != null ? String.format(" %s", user.getMiddleName()) : "",
            user.getLastName()
        )
    );
    return dto;
  }
}
