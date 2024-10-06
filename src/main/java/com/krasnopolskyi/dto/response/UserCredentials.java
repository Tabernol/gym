package com.krasnopolskyi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserCredentials {
    private String username;
    private String password;
}
