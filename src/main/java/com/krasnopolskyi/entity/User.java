package com.krasnopolskyi.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Boolean isActive;
}
