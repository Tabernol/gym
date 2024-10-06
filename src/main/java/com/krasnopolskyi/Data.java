package com.krasnopolskyi;

import com.krasnopolskyi.dto.request.TraineeDto;

import java.time.LocalDate;

public class Data {
   public static final TraineeDto JOHN_TRAINEE = TraineeDto.builder()
            .firstName("John")
            .lastName("Black")
            .dateOfBirth(LocalDate.of(1999, 11, 23))
            .address("new address")
            .build();

}
