package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Builder
public class TrainingDto {
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private Integer trainingType;
    private LocalDate date;
    private Integer duration;
}
