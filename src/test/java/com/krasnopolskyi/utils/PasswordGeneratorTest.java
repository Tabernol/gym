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
    void testGeneratePassword_Length() {
        // Test that the generated password has the correct length (10 characters)
        String password = PasswordGenerator.generatePassword();
        assertEquals(10, password.length());
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

    @Test
    void testGeneratePassword_ContainsDigit() {
        // Test that the generated password contains at least one digit
        String password = PasswordGenerator.generatePassword();
        assertTrue(password.chars().anyMatch(Character::isDigit), "Password should contain at least one digit");
    }

    @Test
    void testGeneratePassword_ContainsSpecialCharacter() {
        // Test that the generated password contains at least one special character
        String specialCharacters = "!@#$%&*()-_=+<>?";
        String password = PasswordGenerator.generatePassword();
        assertTrue(password.chars().anyMatch(c -> specialCharacters.indexOf(c) >= 0), "Password should contain at least one special character");
    }

    @Test
    void testGeneratePassword_IsRandom() {
        // Test that passwords generated consecutively are different
        String password1 = PasswordGenerator.generatePassword();
        String password2 = PasswordGenerator.generatePassword();
        assertNotEquals(password1, password2, "Two generated passwords should not be the same");
    }
}
