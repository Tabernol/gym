package com.krasnopolskyi.entity;

import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
}
