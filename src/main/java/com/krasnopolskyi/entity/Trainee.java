package com.krasnopolskyi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trainee")
@Data
@NoArgsConstructor
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfBirth;
    private String address;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToMany
//    private List<Trainer> trainerList;


//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trainee_id")
//    private List<Training> trainingList;
}
