package com.krasnopolskyi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrainerDto {
    private String firstName;
    private String lastName;
    private Integer specialization;
}
