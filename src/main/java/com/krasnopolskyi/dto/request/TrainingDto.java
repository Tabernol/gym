package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Builder
@Data
public class TrainingDto {
    private Long id;
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private Integer trainingType;
    private LocalDate date;
    private Integer duration;
}
