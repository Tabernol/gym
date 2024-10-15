package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TrainingFilterDto {
    private String owner; // username. owner of training
    private LocalDate startDate;
    private LocalDate endDate;
    private String trainingType;
    private String partner; // name. partner of training
}
