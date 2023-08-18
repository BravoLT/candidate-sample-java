package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.Payment;
import com.bravo.user.dao.model.Profile;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.dto.ProfileDto;
import com.bravo.user.model.dto.UserReadDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ResourceMapper {

  private final ModelMapper modelMapper;

  public ResourceMapper() {
    this.modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
  }

  public <T extends Collection<Address>> List<AddressDto> convertAddresses(final T addresses){
    return addresses.stream().map(this::convertAddress).toList();
  }


  public AddressDto convertAddress(final Address address){
    final AddressDto dto = modelMapper.map(address, AddressDto.class);
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
    return payments.stream().map(this::convertPayment).toList();
  }

  public PaymentDto convertPayment(final Payment payment){
    final String cardNumber = payment.getCardNumber();
    final PaymentDto dto = modelMapper.map(payment, PaymentDto.class);
    if (!StringUtils.isAllBlank(cardNumber) && cardNumber.length() >5) {
      dto.setCardNumberLast4(cardNumber.substring(cardNumber.length() - 5));
    }
    return dto;
  }

  public ProfileDto convertProfile(final Profile profile){
    return modelMapper.map(profile, ProfileDto.class);
  }

  public <T extends Collection<User>> List<UserReadDto> convertUsers(final T users){
    return users.stream().map(this::convertUser).toList();
  }

  public UserReadDto convertUser(final User user){
    final UserReadDto dto = modelMapper.map(user, UserReadDto.class);
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
