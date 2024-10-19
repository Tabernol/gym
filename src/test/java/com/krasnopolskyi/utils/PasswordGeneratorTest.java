package com.krasnopolskyi.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    // Regex patterns to check character groups
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[!@#$%&*()\\-_=+<>?]");



    @Test
    public void testPasswordContainsUppercase() {
        String password = PasswordGenerator.generatePassword();
        assertTrue(UPPERCASE_PATTERN.matcher(password).find(), "Password should contain at least one uppercase letter");
    }

    @Test
    public void testPasswordContainsLowercase() {
        String password = PasswordGenerator.generatePassword();
        assertTrue(LOWERCASE_PATTERN.matcher(password).find(), "Password should contain at least one lowercase letter");
    }

    @Test
    void testGeneratePassword_NotNull() {
        // Test that the generated password is not null
        String password = PasswordGenerator.generatePassword();
        assertNotNull(password);
    }

    @Test
    void testGeneratePassword_ContainsUppercase() {
        // Test that the generated password contains at least one uppercase letter
        String password = PasswordGenerator.generatePassword();
        assertTrue(password.chars().anyMatch(Character::isUpperCase), "Password should contain at least one uppercase letter");
    }

    @Test
    void testGeneratePassword_ContainsLowercase() {
        // Test that the generated password contains at least one lowercase letter
        String password = PasswordGenerator.generatePassword();
        assertTrue(password.chars().anyMatch(Character::isLowerCase), "Password should contain at least one lowercase letter");
    }

    
}
