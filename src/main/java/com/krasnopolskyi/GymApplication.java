package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.facade.MainFacade;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.database.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@EnableScheduling
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        Storage storage = (Storage) context.getBean("storage");
        Map<Integer, TrainingType> trainingTypes = storage.getTrainingTypes();
        Map<Long, User> users = storage.getUsers();
        Map<Long, Trainee> trainees = storage.getTrainees();
        Map<Long, Trainer> trainers = storage.getTrainers();
        Map<Long, Training> trainings = storage.getTrainings();

        log.info("trainingTypes size " + trainingTypes.size());
        log.info("users size " + users.size());
        log.info("trainees size " + trainees.size());
        log.info("trainers size " + trainers.size());
        log.info("trainings size " + trainings.size());

        TraineeDto traineeDto = TraineeDto.builder()
                .firstName("John")
                .lastName("Black")
                .dateOfBirth(LocalDate.of(1999, 11, 23))
                .address("new address")
                .build();

        MainFacade facade = context.getBean(MainFacade.class);

        UserCredentials john = facade.createTrainee(Data.JOHN_TRAINEE);
        UserCredentials john1 = facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.JOHN_TRAINEE);
        UserCredentials john7 = facade.createTrainee(Data.JOHN_TRAINEE);

        log.info(john.toString());
        log.info(john1.toString());
        log.info(john7.toString());


    }
}
