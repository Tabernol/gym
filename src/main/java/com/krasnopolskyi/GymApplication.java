package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.facade.MainFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

@Slf4j
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MainFacade facade = context.getBean(MainFacade.class);

        log.info("========================== GYM ==============================");

        log.info("======================= trainee =============================");
//        TraineeResponseDto trainee = facade.createTrainee(Data.JOHN_TRAINEE);
//        facade.createTrainee(Data.JOHN_TRAINEE_LONG_ADDRESS);
        TraineeResponseDto trainee = facade.findTraineeById(3L);
//        TraineeResponseDto trainee = facade.findTraineeByUsername("john.black");
//        TraineeDto build = TraineeDto.builder()
//                .id(2L)
//                .firstName("NewTrainee")
//                .lastName("Las")
//                .address("ad")
//                .dateOfBirth(LocalDate.of(2023, 1, 1)).build();
//        TraineeResponseDto updateTrainee = facade.updateTrainee(build);
        log.info(trainee.toString());

        log.info("======================= trainer =============================");

//        TrainerResponseDto trainer = facade.createTrainer(Data.ARNI_TRAINER);
//        TrainerResponseDto trainer = facade.createTrainer(Data.TRAINER_NOT_VALID);
//        TrainerDto updateTra = TrainerDto.builder()
//                .id(1L)
//                .firstName("new")
//                .lastName("newLastName")
//                .specialization(12).build();
//
//        TrainerResponseDto trainer2 = facade.updateTrainer(updateTra);
//
//
//        System.out.println(trainer2);
        log.info("===================== training ==============================");
//        TrainingDto first = TrainingDto.builder()
//                .traineeId(3L)
//                .trainerId(2L)
//                .trainingType(1)
//                .date(LocalDate.of(2024, 2, 2))
//                .trainingName("First")
//                .duration(10)
//                .build();
//        Training training = facade.addTraining(first);
//
//        System.out.println("Saved ----   " + training);

    }
}
