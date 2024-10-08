package com.krasnopolskyi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    private Long id;
    private Long  traineeId;
    private Long trainerId;
    private String trainingName;
    private Integer trainingType;
    private LocalDate date;
    private Integer duration;
}
