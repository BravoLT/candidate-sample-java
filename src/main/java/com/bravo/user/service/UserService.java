package com.bravo.user.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.dao.specification.UserNameFuzzySpecification;
import com.bravo.user.dao.specification.UserSpecification;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.exception.ServiceException;
import com.bravo.user.exception.UnauthorizedException;
import com.bravo.user.model.dto.UserAuthDto;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.model.filter.UserNameFuzzyFilter;
import com.bravo.user.utility.AuthUtil;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final ResourceMapper resourceMapper;

  public UserService(UserRepository userRepository, ResourceMapper resourceMapper) {
    this.userRepository = userRepository;
    this.resourceMapper = resourceMapper;
  }

	/**
	 * Creates a new user in the database. Generates a salt and hash for the
	 * password in the process. For added security, the password itself plus the
	 * salt, hash, and so on are cleared from memory upon completion.
	 * 
	 * @param request {@link UserSaveDto}
	 * @return {@link UserReadDto}
	 */
	public UserReadDto create(final UserSaveDto request) {
		try {
			User user = new User(request);
			try {
				user = userRepository.save(user);
				user.clearAuth();
				request.clearAuth();

				LOGGER.info("created user '{}'", user.getId());
				return resourceMapper.convertUser(user);
			} catch (Throwable throwable) {
				// We don't care about the throwable here, we just want to intercept it so we
				// can clear the password from memory, and then pass it on.
				user.clearAuth();
				throw throwable;
			}
		} catch (Throwable throwable) {
			// We don't care about the throwable here, we just want to intercept it so we
			// can clear the password from memory, and then pass it on.
			request.clearAuth();
			throw throwable;
		}
	}

  public UserReadDto retrieve(final String id){
    Optional<User> optional = userRepository.findById(id);
    User user = getUser(id, optional);

    LOGGER.info("found user '{}'", id);
    return resourceMapper.convertUser(user);
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

	/**
	 * 1. Retrieves the hash and salt from the DB. <br/>
	 * 2. Generates a new hash using the password and the salt. <br/>
	 * 3. Compares the two hashes to make sure they are equal. <br/>
	 * 4. Clears the salt, password, and both hashes. <br/>
	 * 5. If the encrypted password and the saved hash are not equal, throw an
	 * exception. <br/>
	 * 
	 * @param userAuthDto {@link UserAuthDto}
	 * @return {@link UserReadDto}
	 */
	public UserReadDto authenticateUser(final UserAuthDto userAuthDto) {
		UserReadDto userReadDto = null;
		boolean isAuthenticated = false;
		try {
			User user = null;
			try {
				Set<String> emailFilter = new HashSet<String>();
				emailFilter.add(userAuthDto.getEmail());
				UserFilter userFilter = UserFilter.builder().emails(emailFilter).build();
				UserSpecification userSpecification = new UserSpecification(userFilter);
				user = getUser(userAuthDto.getEmail(), userRepository.findOne(userSpecification));
			} catch (DataNotFoundException | IncorrectResultSizeDataAccessException exception) {
				// Since we won't get the user at this point, we only need to clear the plain
				// text password from the UserAuthDto. We can safely bail here.
				throw new UnauthorizedException();
			}
			byte[] hash = AuthUtil.hashPassword(userAuthDto.getPassword(), user.getSalt());
			if (Arrays.equals(user.getHash(), hash)) {
				isAuthenticated = true;
			}
			AuthUtil.clearAuth(hash);
			userAuthDto.clearAuth();
			user.clearAuth();
			if (!isAuthenticated) {
				throw new UnauthorizedException();
			}
			userReadDto = resourceMapper.convertUser(user);
		} catch (Throwable throwable) {
			// No matter what breaks, we should clear the password from memory.
			userAuthDto.clearAuth();
			if (throwable instanceof UnauthorizedException) {
				throw (UnauthorizedException) throwable;
			} else {
				// And since we're here, it's probably a good idea replace any other
				// exceptions with a generic ServiceException so someone outside the system
				// can't tell how exactly he broke it.
				LOGGER.error("fatal error while authenticating user '{}'", userAuthDto.getEmail());
				LOGGER.error(throwable.getMessage());
				throw new ServiceException();
			}
		}
		return userReadDto;
	}

}
