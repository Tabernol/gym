package com.krasnopolskyi.entity;

import lombok.*;

import java.time.LocalDate;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    private Long id;
    private Long userId;
    private LocalDate dateOfBirth;
    private String address;
}
