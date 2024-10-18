package com.krasnopolskyi.config;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorConfigTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // Create a new application context with the ValidatorConfig class
        applicationContext = new AnnotationConfigApplicationContext(ValidatorConfig.class);
    }

    @Test
    void testValidatorBeanCreation() {
        // Retrieve the Validator bean from the application context
        Validator validator = applicationContext.getBean(Validator.class);
        assertNotNull(validator, "Validator bean should not be null");

        // Verify the validator is created using a ValidatorFactory
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator expectedValidator = factory.getValidator();

        assertEquals(expectedValidator.getClass(), validator.getClass(),
                "Validator class should match expected Validator class");
    }
}
