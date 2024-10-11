package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Data
public class TraineeDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
