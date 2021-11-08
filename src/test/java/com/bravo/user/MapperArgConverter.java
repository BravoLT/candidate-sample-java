package com.bravo.user;

import com.bravo.user.config.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class MapperArgConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
    try {
      return new AppConfig().objectMapperBuilder().build().readValue(o.toString(), aClass);
    } catch (JsonProcessingException e) {
      throw new ArgumentConversionException("Cannot convert " + o + " to " + aClass.getSimpleName());
    }
  }
}
