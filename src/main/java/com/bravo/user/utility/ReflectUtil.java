package com.bravo.user.utility;

import com.bravo.user.model.dto.ReflectClassDto;
import com.bravo.user.model.dto.ReflectFieldDto;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class ReflectUtil {
  //logging components help developers debug, have logger, handler/appender, layouts/formatters
  //when a logger is called, logging component records the event in log handler and forwards to appropriate appender
  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

  //describe method takes in an instance of an object as a parameter and returns a ReflectClassDto object
  public static ReflectClassDto describe(final Object instance){
    final Class<?> instanceClass = instance.getClass();
    //a java bean is just a standard that specifies - all properties are private, a public no-argument constructor, implements the Serializable interface
    //what is the Serializable interface? Serializable objects can be written to streams, and hence files, object databases, etc.
    BeanInfo beanInfo;
    //try catch loop prevents compile errors in this case IntrospectionException
    try {
      //Introspector! this is new for me too. This class provides a standard way for tools to learn about the
      //the properties, events and methods supposed by a target java bean
      beanInfo = Introspector.getBeanInfo(instanceClass);
    //try/catch and introspection exception here, and call the logger to log the error
    } catch (IntrospectionException e) {
      LOGGER.error("ERROR", e);
      return null;
    }
    //here we will create the new instance of the reflectDTO class that we will return when the method is called
    //Reflection is a fuzzy concept but would love to learn more about it
    //
    final ReflectClassDto reflection = new ReflectClassDto(instanceClass);
    //for each loop
    for(PropertyDescriptor desc : beanInfo.getPropertyDescriptors()){
      //I think this is saying basically if the class equals the name of descriptor we will use a get method on the "Name" property (?)
      if("class".equals(desc.getName())){
        //if condition is true, we continue on in loop
        continue;
      }
      final ReflectFieldDto<?> field = new ReflectFieldDto<>(desc.getPropertyType());
      field.setField(desc.getName());
      field.setValue(invokeReadMethod(desc, instance));
      reflection.getFields().add(field);
    }
    //we return the reflection we created above
    return reflection;
  }
//
  private Object invokeReadMethod(final PropertyDescriptor descriptor, final Object instance){

    try {
      return descriptor.getReadMethod().invoke(instance);

    } catch (IllegalAccessException | InvocationTargetException e) {
      LOGGER.error("ERROR", e);
      return null;
    }
  }
}

/*



 */