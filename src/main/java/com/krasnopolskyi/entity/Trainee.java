package com.krasnopolskyi.entity;

import lombok.*;

import java.time.LocalDate;
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    private Long id;
    private Long userId;
    private LocalDate dateOfBirth;
    private String address;
}
