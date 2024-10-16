package com.krasnopolskyi.dto.request;

import com.krasnopolskyi.validation.Create;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TrainingFilterDto {
    @NotBlank(groups = Create.class, message = "Fill in username for search  by")
    private String owner; // username. owner of training
    private LocalDate startDate;
    private LocalDate endDate;
    private String trainingType;
    private String partner; // name. partner of training
}
