package com.krasnopolskyi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    private Long id;
    private Long userId;
    private Integer specialization;
}
