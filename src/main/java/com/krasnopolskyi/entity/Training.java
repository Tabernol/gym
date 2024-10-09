package com.krasnopolskyi.entity;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class Training {
    private Long id;
    private Long  traineeId;
    private Long trainerId;
    private String trainingName;
    private Integer trainingType;
    private LocalDate date;
    private Integer duration;
}
