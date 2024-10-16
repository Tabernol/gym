package com.krasnopolskyi.dto.request;

import com.krasnopolskyi.validation.Create;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Builder
@Data
public class TrainingDto {
    private Long id;

    @NotNull(message = "Fill in trainee id")
    private Long traineeId;

    @NotNull(message = "Fill in trainer id")
    private Long trainerId;

    @NotBlank(groups = Create.class, message = "Fill in training name")
    @Size(groups = Create.class, min = 2, max = 64, message = "Training name must be between 2 and 64 characters")
    private String trainingName;

    @NotNull(message = "Training type cannot be null")
    private Integer trainingType;

    private LocalDate date;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "The minimum duration is 1 minute")
    private Integer duration;
}
