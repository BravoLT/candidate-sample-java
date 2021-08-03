package com.bravo.user.utility;

import com.bravo.user.model.dto.ReflectClassDto;
import com.bravo.user.model.dto.ReflectFieldDto;
import com.bravo.user.model.filter.DateFilter;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorUtil {

  public static <T> boolean isEmpty(T value, String... excludeFields){

    final ReflectClassDto reflection = ReflectUtil.describe(value);
    if(reflection == null){
      throw new IllegalStateException(String.format("could not describe class: '%s'", value));
    }
    reflection.removeFieldsByNames(excludeFields);
    for(ReflectFieldDto<?> field : reflection.getFields()){
      if(isValid(field.getValue())){
        return false;
      }
    }
    return true;
  }

  public static <T> boolean isInvalid(T value){
    return !isValid(value);
  }

  public static <T> boolean isValid(T value){

    boolean isValid;
    if(value instanceof Collection<?>){
      isValid = isCollectionValid((Collection<?>)value);
    }
    else if(value instanceof DateFilter<?>){
      isValid = isDateFilterValid((DateFilter<?>)value);
    }
    else if(value instanceof String){
      isValid = isStringValid((String)value);
    }
    else if(value instanceof Integer) {
      isValid = isIntegerValid((Integer)value);
    }
    else {
      isValid = value != null;
    }
    return isValid;
  }

  public static <T> boolean isInvalidLength(T value, T length){
    return !isValidLength(value, length);
  }

  public static <T> boolean isValidLength(T value, T length){
    boolean isValid = false;
    if(value instanceof Integer){
       isValid = isIntegerLengthValid((Integer) value, (Integer) length);
      }
    return isValid;
  }


  private static boolean isCollectionValid(final Collection<?> collection){
    return !collection.isEmpty() && !collection.stream()
        .map(ValidatorUtil::isValid)
        .collect(Collectors.toSet())
        .contains(false);
  }

  private static boolean isDateFilterValid(final DateFilter<?> date){
    return isValid(date.getStart()) || isValid(date.getUntil());
  }

  private static boolean isStringValid(final String string){
    return string != null && !string.trim().isEmpty();
  }

  private static boolean isIntegerValid(final Integer integer) {
    if(integer == null || integer == 0)
      return false;
    String onlyDigits = "\\d+";
    String inString = integer.toString();
    return inString.matches(onlyDigits);
  }

  private static boolean isIntegerLengthValid(final Integer integer, final Integer length) {
    return String.valueOf(integer).length() == length;
  }
}
