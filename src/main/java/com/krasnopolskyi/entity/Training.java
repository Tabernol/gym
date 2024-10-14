package com.krasnopolskyi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long trainerId;
    private Long traineeId;
    private String trainingName;
    @Column(name = "training_date")
    private LocalDate date;
    @Column(name = "training_duration")
    private Integer duration;


    @OneToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
}
