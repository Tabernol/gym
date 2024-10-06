package com.krasnopolskyi.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Trainer {
    private Long id;
    private Long userId;
    private Integer specialization;
}
