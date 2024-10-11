package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TraineeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
