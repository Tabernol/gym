package com.krasnopolskyi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer")
@Data
@NoArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingType specialization;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
