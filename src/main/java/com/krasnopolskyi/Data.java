package com.krasnopolskyi;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class Data {
    public static final TraineeDto JOHN_TRAINEE = TraineeDto.builder()
            .firstName("John")
            .lastName("Black")
            .dateOfBirth(LocalDate.of(1999, 11, 23))
            .address("new address")
            .build();

    public static final TrainerDto ARNI_TRAINER = new TrainerDto("Arni", "Schwarz", 1);
    public static final TrainerDto TRAINER_NOT_VALID = new TrainerDto("Arni", "Schwarz", 10);

    public static final TrainingDto trainingValid = TrainingDto.builder()
            .trainerId(1L)
            .traineeId(1L)
            .trainingType(1)
            .date(LocalDate.of(2024, 1, 1))
            .duration(3600)
            .trainingName("monday")
            .build();

    public static final TrainingDto trainingInvalidSpecialization = TrainingDto.builder()
            .trainerId(1L)
            .traineeId(1L)
            .trainingType(2)
            .date(LocalDate.of(2024, 1, 1))
            .duration(3600)
            .trainingName("monday")
            .build();

    public static final TrainingDto trainingInvalidTrainee = TrainingDto.builder()
            .trainerId(100L)
            .traineeId(1L)
            .trainingType(2)
            .date(LocalDate.of(2024, 1, 1))
            .duration(3600)
            .trainingName("monday")
            .build();

    public static final TrainingDto trainingInvalidTrainer = TrainingDto.builder()
            .trainerId(1L)
            .traineeId(100L)
            .trainingType(2)
            .date(LocalDate.of(2024, 1, 1))
            .duration(3600)
            .trainingName("monday")
            .build();
}
