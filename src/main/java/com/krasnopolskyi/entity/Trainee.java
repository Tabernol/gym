package com.krasnopolskyi.entity;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class Trainee {
    private Long id;
    private Long userId;
    private LocalDate dateOfBirth;
    private String address;
}
