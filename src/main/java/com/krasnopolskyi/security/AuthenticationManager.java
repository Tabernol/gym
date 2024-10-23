package com.krasnopolskyi.security;

import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.exception.AccessException;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple class for Authentication. Provide functional for log in, but does not for log out
 */
@Component
@Slf4j
public class AuthenticationManager {
    private final UserService userService;
    private final Set<String> users = new HashSet<>();

    private static final String AUTH_SUCCESSFUL = "Authentication successful";
    private static final String AUTH_FAILED = "Authentication failed";

    public AuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    public String login(UserCredentials credentials) {
        try {
            boolean success = userService.checkCredentials(credentials);
            if (success) {
                users.add(credentials.username());
                return AUTH_SUCCESSFUL;
            } else {
                log.warn("Password is wrong");
            }
        } catch (EntityException e) {
            log.warn("User not found " + e);
        }
        return AUTH_FAILED;
    }

    public void checkPermissions(String username) throws AccessException {
        if (!users.contains(username)) {
            log.warn("Access was denied for user " + username);
            throw new AccessException("Please check your credentials and try again");
        }
    }
}
