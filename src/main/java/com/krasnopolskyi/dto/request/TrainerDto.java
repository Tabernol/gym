package com.krasnopolskyi.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrainerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer specialization;
}
