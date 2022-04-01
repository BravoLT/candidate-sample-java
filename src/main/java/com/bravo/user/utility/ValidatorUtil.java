package com.bravo.user.utility;

import com.bravo.user.model.dto.ReflectClassDto;
import com.bravo.user.model.dto.ReflectFieldDto;
import com.bravo.user.model.filter.DateFilter;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorUtil {

  //<T> means type generic, ... means that we might be getting multiple parameters
  //this method is checking whether passed in value is empty
  public static <T> boolean isEmpty(T value, String... excludeFields){
    //I learned that a reflection class allows us to inspect and modify an object's behavior at runtime
    //instantiating a variable of the reflection class data transfer object
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
//validation methods
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
    else {
      isValid = value != null;
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

  public static String removeControlCharacters(final String string){
    if(string == null){
      return null;
    }
    return string.replaceAll("[!*%]", "");
  }
}
