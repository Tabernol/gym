package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@ToString
public class TraineeDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
