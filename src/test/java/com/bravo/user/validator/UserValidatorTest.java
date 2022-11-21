package com.bravo.user.validator;

import com.bravo.App;
import com.bravo.user.MapperArgConverter;
import com.bravo.user.enumerator.Crud;
import com.bravo.user.model.dto.UserSaveDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserValidatorTest {

  @Autowired
  private UserValidator userValidator;

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/UserValidatorTest/validateCreateTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  public void validateCreate(
      @ConvertWith(MapperArgConverter.class) UserSaveDto userSaveDto,
      boolean isValid
  ) {
    final Executable executable = () -> userValidator.validate(
        Crud.CREATE, userSaveDto, createBindingResult(userSaveDto)
    );
    if (isValid) {
      assertDoesNotThrow(executable);
    } else {
      assertThrows(BindException.class, executable);
    }
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/UserValidatorTest/validateUpdateTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  public void validateUpdate(
      @ConvertWith(MapperArgConverter.class) UserSaveDto userSaveDto,
      boolean isValid
  ) {
    final Executable executable = () -> userValidator.validate(
        Crud.UPDATE, userSaveDto, createBindingResult(userSaveDto)
    );
    if (isValid) {
      assertDoesNotThrow(executable);
    } else {
      assertThrows(BindException.class, executable);
    }
  }

  private BeanPropertyBindingResult createBindingResult(final UserSaveDto userSaveDto){
    return new BeanPropertyBindingResult(userSaveDto, "userSaveDto");
  }
}