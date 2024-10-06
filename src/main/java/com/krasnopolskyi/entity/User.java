package com.krasnopolskyi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private boolean isActive;
}
