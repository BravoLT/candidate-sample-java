package com.bravo.user.service;

import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.dao.specification.UserNameFuzzySpecification;
import com.bravo.user.dao.specification.UserSpecification;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.exception.UserNamePasswordException;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.dto.PasswordValidateDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.model.filter.UserNameFuzzyFilter;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final ResourceMapper resourceMapper;
  private final EncryptService encryptService;

  public UserService(UserRepository userRepository, ResourceMapper resourceMapper, EncryptService encryptService) {
    this.userRepository = userRepository;
    this.resourceMapper = resourceMapper;
    this.encryptService = encryptService;
  }

  public UserReadDto create(final UserSaveDto request){
    final String encryptedPassword = encryptService.encrypt(request.getPassword());

    request.setPassword(encryptedPassword);
    
    
    User user = userRepository.save(new User(request));

    LOGGER.info("created user '{}'", user.getId());
    return resourceMapper.convertUser(user);
  }

  public UserReadDto retrieve(final String id){
    Optional<User> optional = userRepository.findById(id);
    User user = getUser(id, optional);

    LOGGER.info("found user '{}'", id);
    return resourceMapper.convertUser(user);
  }
  
  public PasswordValidateDto validate(final PasswordValidateDto passwordValidateDto, final PageRequest pageRequest, final HttpServletResponse httpResponse){
      final String encryptedPassword = encryptService.encrypt(passwordValidateDto.getPassword());
      User user = userRepository.findByEmail(passwordValidateDto.getEmail());
      
      if(!passwordValidateDto.getEmail().matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")){
      	throw new UserNamePasswordException("Invalid Email Format");
      }
      
      if((user == null) || (!encryptedPassword.equals(user.getPassword()))){
      	throw new UserNamePasswordException("Invalid Email/Password combination");
      }  
   
      return passwordValidateDto;
  }
  
  public List <UserReadDto> retrieveByName(
      final String name,
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  ){
    final UserNameFuzzySpecification specification = new UserNameFuzzySpecification(UserNameFuzzyFilter.builder()
        .name(name)
        .build());
    final Page<User> userPage = userRepository.findAll(specification, pageRequest);
    final List<UserReadDto> users = resourceMapper.convertUsers(userPage.getContent());
    LOGGER.info("found {} user(s)", users.size());

    PageUtil.updatePageHeaders(httpResponse, userPage, pageRequest);
    return users;
  }

  public List<UserReadDto> retrieve(
      final UserFilter filter,
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  ){
    final UserSpecification specification = new UserSpecification(filter);
    final Page<User> userPage = userRepository.findAll(specification, pageRequest);
    final List<UserReadDto> users = resourceMapper.convertUsers(userPage.getContent());
    LOGGER.info("found {} user(s)", users.size());

    PageUtil.updatePageHeaders(httpResponse, userPage, pageRequest);
    return users;
  }

  public UserReadDto update(final String id, final UserSaveDto request){

    final Optional<User> optional = userRepository.findById(id);
    final User user = getUser(id, optional);
    user.setUpdated(LocalDateTime.now());

    if(ValidatorUtil.isValid(request.getFirstName())){
      user.setFirstName(request.getFirstName());
    }
    if(ValidatorUtil.isValid(request.getMiddleName())){
      user.setMiddleName(request.getMiddleName());
    }
    if(ValidatorUtil.isValid(request.getLastName())){
      user.setLastName(request.getLastName());
    }
    if(ValidatorUtil.isValid(request.getPhoneNumber())){
      user.setPhoneNumber(request.getPhoneNumber());
    }

    if(ValidatorUtil.isValid(request.getEmail())){
      user.setEmail(request.getEmail());
    }
    
    final User updated = userRepository.save(user);

    LOGGER.info("updated user '{}'", updated.getId());
    return resourceMapper.convertUser(updated);
  }

  public boolean delete(final String id){

    final Optional<User> optional = userRepository.findById(id);
    final User user = getUser(id, optional);

    userRepository.deleteById(user.getId());

    final boolean isDeleted = userRepository.findById(id).isEmpty();
    if(isDeleted){
      LOGGER.info("deleted user '{}'", id);
    } else {
      LOGGER.warn("failed to delete user '{}'", id);
    }
    return isDeleted;
  }

  private User getUser(final String id, final Optional<User> user){

    if(user.isEmpty()){
      final String message = String.format("user '%s' doesn't exist", id);
      LOGGER.warn(message);
      throw new DataNotFoundException(message);
    }
    return user.get();
  }
}
