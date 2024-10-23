package com.krasnopolskyi.validation.annotation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomAgeValidatorTest {

    private CustomAgeValidator customAgeValidator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customAgeValidator = new CustomAgeValidator();
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(violationBuilder);
    }

    @Test
    public void testNullDateOfBirth_ShouldBeValid() {
        // Null date of birth should be considered valid
        assertTrue(customAgeValidator.isValid(null, context));
    }

    @Test
    public void testFutureDateOfBirth_ShouldBeInvalid() {
        // A date of birth in the future should be invalid
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertFalse(customAgeValidator.isValid(futureDate, context));

        // Verify that the custom error message is set for future date
        Mockito.verify(context).disableDefaultConstraintViolation();
        Mockito.verify(context).buildConstraintViolationWithTemplate("Date of birth cannot be in the future.");
    }

    @Test
    public void testOverMaxAge_ShouldBeInvalid() {
        // A date of birth that exceeds the max age of 100 should be invalid
        LocalDate overAge = LocalDate.now().minusYears(101);
        assertFalse(customAgeValidator.isValid(overAge, context));

        // Verify that the custom error message is set for exceeding the age limit
        Mockito.verify(context).disableDefaultConstraintViolation();
        Mockito.verify(context).buildConstraintViolationWithTemplate(Mockito.contains("Are you sure that you what to register?"));
    }

    @Test
    public void testValidAge_ShouldBeValid() {
        // A valid date of birth within the 100-year limit should be valid
        LocalDate validAge = LocalDate.now().minusYears(25);
        assertTrue(customAgeValidator.isValid(validAge, context));
    }

    @Test
    public void testExactlyMaxAge_ShouldBeValid() {
        // A date of birth exactly at the 100-year limit should be valid
        LocalDate exactlyMaxAge = LocalDate.now().minusYears(100);
        assertTrue(customAgeValidator.isValid(exactlyMaxAge, context));
    }
}
