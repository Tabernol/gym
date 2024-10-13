package com.krasnopolskyi.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "training_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "training_type_name")
    private String type;
}
