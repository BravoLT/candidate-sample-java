package com.bravo.user;

import com.bravo.user.config.AppConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {
    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapperBuilder().build();

    @SneakyThrows
    public String readFromFile(String file)  {
        var url = TestUtils.class.getResource(file);
        return Files.readString(Path.of(url.getPath()));
    }

    @SneakyThrows
    public static <T> T deserializeFromJson(String json, TypeReference<T> clazz) {
        return OBJECT_MAPPER.readValue(json, clazz);
    }
}
