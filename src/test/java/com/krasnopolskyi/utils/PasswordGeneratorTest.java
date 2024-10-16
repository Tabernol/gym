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
    public void testPasswordLength() {
        String password = PasswordGenerator.generatePassword();
        assertEquals(10, password.length(), "Password should be exactly 10 characters long");
    }

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
    public void testPasswordContainsDigit() {
        String password = PasswordGenerator.generatePassword();
        assertTrue(DIGIT_PATTERN.matcher(password).find(), "Password should contain at least one digit");
    }

    @Test
    public void testPasswordContainsSpecialCharacter() {
        String password = PasswordGenerator.generatePassword();
        assertTrue(SPECIAL_CHARACTER_PATTERN.matcher(password).find(), "Password should contain at least one special character");
    }

    @Test
    public void testPasswordIsRandom() {
        String password1 = PasswordGenerator.generatePassword();
        String password2 = PasswordGenerator.generatePassword();
        assertNotEquals(password1, password2, "Generated passwords should not be the same");
    }

}
