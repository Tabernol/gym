package com.krasnopolskyi.dto.request;

import java.time.LocalDate;

public class TrainingDto {
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private String trainingType;
    private LocalDate date;
    private Integer duration;
}
