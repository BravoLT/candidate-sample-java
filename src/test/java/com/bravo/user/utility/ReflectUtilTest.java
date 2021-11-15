package com.bravo.user.utility;

import com.bravo.user.model.dto.ReflectClassDto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.bravo.user.utility.ReflectUtil.*;

// Testing on exceptions is quite a headache, especially when it's something
// high-level like reflections and making sure you can throw an IntrospectionException

class ReflectUtilTest {

    Object testInstance1 = new Object();
    //List<String> testInstance2 = new ArrayList<>();
    ReflectClassDto testDto = new ReflectClassDto(testInstance1.getClass());

    @Test
    void reflectUtilDescribeReturnsReflectClassDto() {
        assertEquals(describe(testInstance1), testDto);
    }

}