package com.krasnopolskyi.entity;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class Trainer {
    private Long id;
    private Long userId;
    private Integer specialization;
}
