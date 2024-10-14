package com.krasnopolskyi;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;

import java.time.LocalDate;

public class Data {
    public static final TraineeDto JOHN_TRAINEE = TraineeDto.builder().firstName("John").lastName("Black").dateOfBirth(LocalDate.of(1999, 11, 23)).address("new address").build();
    public static final TraineeDto JOHN_TRAINEE_LONG_ADDRESS = TraineeDto.builder().firstName("John").lastName("Smith").dateOfBirth(LocalDate.of(1999, 11, 23)).address("More than 300. In today’s fast-paced world, staying organized is crucial for success. Whether you’re managing a team, handling projects, or balancing personal commitments, effective organization can lead to increased productivity and reduced stress. Utilizing digital tools, setting clear priorities, and regularly reviewing tasks can greatly enhance your efficiency and help you achieve your goals.").build();
    public static final TraineeDto SHORT_NAME_TRAINEE = TraineeDto.builder().firstName("J").lastName("Bl").dateOfBirth(LocalDate.of(1999, 11, 23)).address("new address").build();
    public static final TrainerDto ARNI_TRAINER = TrainerDto.builder().firstName("Arni").lastName("Schwartz").specialization(1).build();
    public static final TrainerDto TRAINER_NOT_VALID = TrainerDto.builder().firstName("Arni2").lastName("Schwartz2").specialization(100).build();
    public static final TrainingDto TRAINING_VALID = TrainingDto.builder().trainerId(1L).traineeId(1L).trainingType(1).date(LocalDate.of(2024, 1, 1)).duration(3600).trainingName("monday").build();
    public static final TrainingDto TRAINING_INVALID_SPECIALIZATION = TrainingDto.builder().trainerId(1L).traineeId(1L).trainingType(2).date(LocalDate.of(2024, 1, 1)).duration(3600).trainingName("monday").build();
    public static final TrainingDto TRAINING_INVALID_TRAINEE = TrainingDto.builder().trainerId(100L).traineeId(1L).trainingType(2).date(LocalDate.of(2024, 1, 1)).duration(3600).trainingName("monday").build();
    public static final TrainingDto TRAINING_INVALID_TRAINER = TrainingDto.builder().trainerId(1L).traineeId(100L).trainingType(2).date(LocalDate.of(2024, 1, 1)).duration(3600).trainingName("monday").build();
}
