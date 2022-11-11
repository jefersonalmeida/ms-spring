package com.jefersonalmeida.evaluator.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.concurrent.Callable;

public enum Json {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(Json.class);

    public static ObjectMapper mapper() {
        return INSTANCE.mapper.copy();
    }

    public static String writeValueAsString(final Object obj) {
        return invoke(() -> INSTANCE.mapper.writeValueAsString(obj));
    }

    public static <T> T readValue(final String json, final Class<T> clazz) {
        return invoke(() -> INSTANCE.mapper.readValue(json, clazz));
    }

    private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .dateFormat(new StdDateFormat())
            .featuresToDisable(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                    DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,

                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            )
            .modules(
                    new JavaTimeModule(),
                    new Jdk8Module()
            )
//            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .build();

    private static <T> T invoke(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("Json Erro: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
