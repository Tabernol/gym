package com.krasnopolskyi;

import com.krasnopolskyi.config.HibernateConfig;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.facade.MainFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Slf4j
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
        MainFacade facade = context.getBean(MainFacade.class);
        log.info("========================== GYM ==============================");

        log.info("======================= trainee =============================");
        TraineeResponseDto trainee = facade.createTrainee(Data.JOHN_TRAINEE);
//        TraineeResponseDto trainee = facade.createTrainee(Data.JOHN_TRAINEE_LONG_NAME);
//        facade.createTrainee(Data.JOHN_TRAINEE_LONG_ADDRESS);
//        TraineeResponseDto trainee = facade.findTraineeById(1L);
//        TraineeResponseDto trainee = facade.findTraineeByUsername("john.black");
//        TraineeDto build = TraineeDto.builder()
//                .id(2L)
//                .firstName("NewTrainee")
//                .lastName("Las")
//                .address("ad")
//                .dateOfBirth(LocalDate.of(2023, 1, 1)).build();
//        TraineeResponseDto updateTrainee = facade.updateTrainee(build);
        log.info(trainee.toString());


//        facade.deleteTrainee("john.black");

        log.info("======================= trainer =============================");

//        TrainerResponseDto trainer = facade.createTrainer(Data.ARNI_TRAINER);
//        TrainerResponseDto trainer = facade.createTrainer(Data.TRAINER_NOT_VALID);
//        TrainerDto updateTra = TrainerDto.builder()
//                .id(1L)
//                .firstName("new")
//                .lastName("new")
//                .specialization(1).build();
//
//        TrainerResponseDto trainer2 = facade.updateTrainer(updateTra);
//        log.info(trainer2.toString());
//
//
//        log.info(trainer.toString());
        log.info("===================== training ==============================");
//        TrainingDto first = TrainingDto.builder()
//                .traineeId(1L)
//                .trainerId(1L)
//                .trainingType(1)
//                .date(LocalDate.of(2024, 2, 2))
//                .trainingName("First")
//                .duration(10)
//                .build();
//        TrainingResponseDto training = facade.addTraining(first);
//        log.info(training.toString());
//
//        TrainingResponseDto training1 = facade.findTrainingById(1L);
//        log.info(training1.toString());
//
//        System.out.println("Saved ----   " + training);
//        TrainingFilterDto filterDto = TrainingFilterDto.builder()
//                .owner("john.black")
//                .startDate(LocalDate.of(2024,1,1))
//                .endDate(LocalDate.of(2025,1,1))
//                .trainingType("Aerobics")
//                .build();
//        List<TrainingResponseDto> trainings = facade.getAllTrainingsByUsernameAndFilter(filterDto);
//        log.info("SIZE " + trainings.size());
//        log.info(trainings.toString());
//        System.out.println(trainings);
        log.info("================= update trainers ============================");
//        TraineeDto traineeDto = TraineeDto.builder()
//                .id(1L)
//                .build();
//        TrainerDto trainerDto = TrainerDto.builder()
//                .id(3L)
//                .build();
//
//        List<TrainerResponseDto> trainerResponseDtos = facade.updateTrainers(traineeDto, List.of(trainerDto));
//        System.out.println(trainerResponseDtos);
        log.info("=============== not assigned trainers ====================");

//        List<TrainerResponseDto> trainers = facade.getAllNotAssignedTrainersByTraineeUsername("john.black");
//        log.info(trainers.toString());

    }
}
