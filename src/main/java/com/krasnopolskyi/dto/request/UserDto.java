package com.krasnopolskyi.dto.request;

import com.krasnopolskyi.entity.Role;

public record UserDto(String firstName, String lastName, Role role) {
}
