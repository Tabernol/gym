package com.krasnopolskyi.config;

import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.database.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.yaml")
@ComponentScan("com.krasnopolskyi")
public class AppConfiguration {

    @Bean
    public Map<Integer, TrainingType> trainingTypesMap() {
        Map<Integer, TrainingType> trainingTypeMap = new HashMap<>();
        trainingTypeMap.put(1, new TrainingType(1, "Cardio"));
        return trainingTypeMap;
    }

    @Bean
    public Map<Long, User> usersMap() {
        return new HashMap<>();
    }
    @Bean
    public Map<Long, Trainee> traineesMap() {
        return new HashMap<>();
    }
    @Bean
    public Map<Long, Trainer> trainersMap() {
        return new HashMap<>();
    }
    @Bean
    public Map<Long, Training> trainingsMap() {
        return new HashMap<>();
    }

    @Bean
    public Storage storage(
            Map<Integer, TrainingType> trainingTypes,
            Map<Long, User> users,
            Map<Long, Trainee> trainees,
            Map<Long, Trainer> trainers,
            Map<Long, Training> trainings) {
        return new Storage(trainingTypes, users, trainees, trainers, trainings);
    }
}
