package com.krasnopolskyi.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CommonValidatorTest {

    private CommonValidator commonValidator;

    @Mock
    private Validator validator;

    @Mock
    private ConstraintViolation<Object> constraintViolation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commonValidator = new CommonValidator(validator);
    }

    @Test
    public void testValidate_WithNoViolations_ShouldNotThrowException() {
        // Mock an empty set of violations (valid object)
        Object validObject = new Object();
        when(validator.validate(validObject, new Class<?>[0])).thenReturn(Collections.emptySet());

        // Perform validation and expect no exception
        assertDoesNotThrow(() -> commonValidator.validate(validObject));
    }

    @Test
    public void testValidate_WithViolations_ShouldThrowConstraintViolationException() {
        // Mock a set with one violation (invalid object)
        Object invalidObject = new Object();
        Set<ConstraintViolation<Object>> violations = Set.of(constraintViolation);
        when(validator.validate(invalidObject, new Class<?>[0])).thenReturn(violations);

        // Perform validation and expect ConstraintViolationException
        assertThrows(ConstraintViolationException.class, () -> commonValidator.validate(invalidObject));
    }

    @Test
    public void testValidate_WithCustomGroup_ShouldInvokeValidatorWithGroup() {
        // Mock an empty set of violations (valid object)
        Object validObject = new Object();
        Class<?>[] groups = {DefaultGroup.class};
        when(validator.validate(validObject, groups)).thenReturn(Collections.emptySet());

        // Perform validation with the group and expect no exception
        assertDoesNotThrow(() -> commonValidator.validate(validObject, groups));
    }

    @Test
    public void testValidate_WithCustomGroupAndViolations_ShouldThrowConstraintViolationException() {
        // Mock a set with one violation (invalid object with group)
        Object invalidObject = new Object();
        Class<?>[] groups = {DefaultGroup.class};
        Set<ConstraintViolation<Object>> violations = Set.of(constraintViolation);
        when(validator.validate(invalidObject, groups)).thenReturn(violations);

        // Perform validation and expect ConstraintViolationException
        assertThrows(ConstraintViolationException.class, () -> commonValidator.validate(invalidObject, groups));
    }

    // Dummy group for testing purposes
    private interface DefaultGroup {
    }
}
