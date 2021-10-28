/* (C)2021 */
package com.bravo.user.service;

import static com.bravo.user.constants.Constants.ErrorMessages.INCORRECT_USER_EMAIL_OR_PASSWORD;

import com.bravo.user.dao.converters.ColumnEncryptor;
import com.bravo.user.dao.model.User;
import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.UserRepository;
import com.bravo.user.dao.specification.UserEmailFuzzySpecification;
import com.bravo.user.dao.specification.UserNameFuzzySpecification;
import com.bravo.user.dao.specification.UserSpecification;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.exception.InternalServerErrorException;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.dto.UserSaveDto;
import com.bravo.user.model.filter.UserEmailFuzzyFilter;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ResourceMapper resourceMapper;

    public UserService(UserRepository userRepository, ResourceMapper resourceMapper) {
        this.userRepository = userRepository;
        this.resourceMapper = resourceMapper;
    }

    public UserReadDto create(final UserSaveDto request) {
        try {
            User user = userRepository.save(new User(request));

            LOGGER.info("created user '{}'", user.getId());
            UserReadDto userReadDto = resourceMapper.convertUser(user);
            userReadDto.setPassword("XXXXXX");
            return userReadDto;
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                final String message = String.format("user '%s' already exist", request.getEmail());
                LOGGER.warn(message);
                throw new InternalServerErrorException(message);
            } else {
                final String message = "Something went wrong or data not found ...";
                LOGGER.warn(message);
                throw new DataNotFoundException(message);
            }
        }
    }

    public UserReadDto retrieve(final String id) {
        Optional<User> optional = userRepository.findById(id);
        User user = getUser(id, optional);

        LOGGER.info("found user '{}'", id);
        UserReadDto userReadDto = resourceMapper.convertUser(user);
        userReadDto.setPassword("XXXXXX");
        return userReadDto;
    }

    public UserReadDto retrieveByEmailAndPassword(final String email, final String password) {
        final UserEmailFuzzySpecification specification =
                new UserEmailFuzzySpecification(
                        UserEmailFuzzyFilter.builder().email(email).build());
        final List<User> usersList = userRepository.findAll(specification);
        final List<UserReadDto> users = resourceMapper.convertUsers(usersList);
        if (users == null || users.isEmpty()) {
            LOGGER.warn(INCORRECT_USER_EMAIL_OR_PASSWORD);
            throw new DataNotFoundException(INCORRECT_USER_EMAIL_OR_PASSWORD);
        }
        LOGGER.info("found {} user(s)", users.size());
        Optional<UserReadDto> user =
                users.stream().filter(u -> u.getEmail().equals(email)).findAny();
        if (user.isPresent()) {
            UserReadDto userFound = user.get();
            if (password != null && !password.isBlank()) {
                try {
                    ColumnEncryptor encryptor = new ColumnEncryptor();
                    String encryptedPassword = encryptor.convertToDatabaseColumn(password);
                    if (encryptor
                            .convertToDatabaseColumn(userFound.getPassword())
                            .equals(encryptedPassword)) {
                        final String message = "User is validated";
                        LOGGER.info(message);
                        userFound.setPassword("XXXXXX");
                        return userFound;
                    } else {
                        LOGGER.warn(INCORRECT_USER_EMAIL_OR_PASSWORD);
                        throw new DataNotFoundException(INCORRECT_USER_EMAIL_OR_PASSWORD);
                    }
                } catch (Exception e) {
                    LOGGER.warn(INCORRECT_USER_EMAIL_OR_PASSWORD);
                    throw new DataNotFoundException(INCORRECT_USER_EMAIL_OR_PASSWORD);
                }
            } else {
                final String message = "Password cannot be empty.";
                LOGGER.warn(message);
                throw new DataNotFoundException(message);
            }
        } else {
            LOGGER.warn(INCORRECT_USER_EMAIL_OR_PASSWORD);
            throw new DataNotFoundException(INCORRECT_USER_EMAIL_OR_PASSWORD);
        }
    }

    public List<UserReadDto> retrieveByName(
            final String name,
            final PageRequest pageRequest,
            final HttpServletResponse httpResponse) {
        final UserNameFuzzySpecification specification =
                new UserNameFuzzySpecification(UserNameFuzzyFilter.builder().name(name).build());
        final Page<User> userPage = userRepository.findAll(specification, pageRequest);
        final List<UserReadDto> users = resourceMapper.convertUsers(userPage.getContent());
        LOGGER.info("found {} user(s)", users.size());

        PageUtil.updatePageHeaders(httpResponse, userPage, pageRequest);
        for (UserReadDto user : users) {
            user.setPassword("XXXXXX");
        }
        return users;
    }

    public List<UserReadDto> retrieve(
            final UserFilter filter,
            final PageRequest pageRequest,
            final HttpServletResponse httpResponse) {
        final UserSpecification specification = new UserSpecification(filter);
        final Page<User> userPage = userRepository.findAll(specification, pageRequest);
        final List<UserReadDto> users = resourceMapper.convertUsers(userPage.getContent());
        LOGGER.info("found {} user(s)", users.size());

        PageUtil.updatePageHeaders(httpResponse, userPage, pageRequest);
        for (UserReadDto user : users) {
            user.setPassword("XXXXXX");
        }
        return users;
    }

    public UserReadDto update(final String id, final UserSaveDto request) {

        final Optional<User> optional = userRepository.findById(id);
        final User user = getUser(id, optional);
        user.setUpdated(LocalDateTime.now());

        if (ValidatorUtil.isValid(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        if (ValidatorUtil.isValid(request.getMiddleName())) {
            user.setMiddleName(request.getMiddleName());
        }
        if (ValidatorUtil.isValid(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (ValidatorUtil.isValid(request.getPhoneNumber())) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        final User updated = userRepository.save(user);

        LOGGER.info("updated user '{}'", updated.getId());
        UserReadDto userReadDto = resourceMapper.convertUser(updated);
        userReadDto.setPassword("XXXXXX");
        return userReadDto;
    }

    public boolean delete(final String id) {

        final Optional<User> optional = userRepository.findById(id);
        final User user = getUser(id, optional);

        userRepository.deleteById(user.getId());

        final boolean isDeleted = userRepository.findById(id).isEmpty();
        if (isDeleted) {
            LOGGER.info("deleted user '{}'", id);
        } else {
            LOGGER.warn("failed to delete user '{}'", id);
        }
        return isDeleted;
    }

    private User getUser(final String id, final Optional<User> user) {

        if (user.isEmpty()) {
            final String message = String.format("user '%s' doesn't exist", id);
            LOGGER.warn(message);
            throw new DataNotFoundException(message);
        }
        return user.get();
    }
}
