package com.krasnopolskyi.config;

import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.database.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.yaml")
@ComponentScan("com.krasnopolskyi")
@Slf4j
@EnableAspectJAutoProxy // enable AOP
public class AppConfiguration {

    @Bean
    public Map<Integer, TrainingType> trainingTypesMap() {return new HashMap<>();}
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
