package com.krasnopolskyi.entity;

import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private Long id;
    private Long userId;
    private Integer specialization;
}
