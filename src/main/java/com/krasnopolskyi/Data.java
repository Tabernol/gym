package com.krasnopolskyi;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;

import java.time.LocalDate;

public class Data {
   public static final TraineeDto JOHN_TRAINEE = TraineeDto.builder()
            .firstName("John")
            .lastName("Black")
            .dateOfBirth(LocalDate.of(1999, 11, 23))
            .address("new address")
            .build();

    public static final TrainerDto ARNI_TRAINER = new TrainerDto("Arni", "Schwarz", 1);
    public static final TrainerDto TRAINER_NOT_VALID = new TrainerDto("Arni", "Schwarz", 10);

}
