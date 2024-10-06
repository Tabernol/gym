package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Builder
@Getter
public class TraineeUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
