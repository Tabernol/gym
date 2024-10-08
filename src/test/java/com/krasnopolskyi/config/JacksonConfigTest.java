package com.krasnopolskyi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {

    @Test
    void testObjectMapperBean() {
        // Load the configuration class
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JacksonConfig.class)) {
            // Retrieve the ObjectMapper bean
            ObjectMapper objectMapper = (ObjectMapper) context.getBean("objectMapper");

            // Verify that the ObjectMapper is not null
            assertNotNull(objectMapper);
        }
    }
}
