package com.krasnopolskyi;

import com.krasnopolskyi.config.HibernateConfig;
import com.krasnopolskyi.dto.request.*;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.facade.MainFacade;
import com.krasnopolskyi.security.AuthenticationManager;
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
        AuthenticationManager manager = context.getBean(AuthenticationManager.class);
        log.info("========================== GYM ==============================");
        String authMessage1 = manager.login(new UserCredentials("usain.bolt", "root"));
        log.info("Usain bolt " + authMessage1);
//        // need to change passwords for new users
//        String authMessage2 = manager.login(new UserCredentials("john.black", "C3nRq<fuSR"));
//        log.info("Trainee " + authMessage2);
//        // need to change passwords for new users
//        String authMessage3 = manager.login(new UserCredentials("tiger.woods", "3$5lVLHyEZ"));
//        log.info("Trainee " + authMessage3);

//        facade.changePassword("root2", "usain.bolt");

//        facade.changeActivityStatus("jane.smith", "usain.bolt");
//        facade.changeActivityStatus("jane.smith", "usain.bolt");
//        facade.changeActivityStatus("jane.smith", "usain.bolt");


        log.info("======================= trainee =============================");
//        TraineeResponseDto trainee1 = facade.createTrainee(Data.JOHN_TRAINEE);
//        TraineeResponseDto trainee2 = facade.createTrainee(Data.JOHN_TRAINEE_LONG_NAME);
//        TraineeResponseDto trainee3 = facade.createTrainee(Data.JOHN_TRAINEE_SHORT_NAME);
//        TraineeResponseDto trainee4 = facade.createTrainee(Data.JOHN_TRAINEE_VERY_OLD);
//        TraineeResponseDto trainee5 = facade.createTrainee(Data.JOHN_TRAINEE_FROM_FUTURE);
//        TraineeResponseDto trainee6 = facade.createTrainee(Data.JOHN_TRAINEE_WITHOUT_ADDITIONAL_DATA);
//        TraineeResponseDto trainee7 = facade.createTrainee(Data.JOHN_TRAINEE_LONG_ADDRESS);
        ///////////////////////////////////////////////////////////////////////////////////////
//        TraineeResponseDto trainee = facade.findTraineeByUsername("john.black", "john.black");
//        // not found
//        facade.findTraineeByUsername("john.black55", "john.black");
        ///////////////////////////////////////////////////////////////////
//        TraineeDto traineeToUpdate = TraineeDto.builder()
//                .id(4L)
//                .firstName("John2")
//                .lastName("Doe2")
//                .address("Move to another location")
//                .dateOfBirth(LocalDate.of(2013, 1, 1)).build();
//        TraineeResponseDto updateTrainee = facade.updateTrainee(traineeToUpdate, "usain.bolt");
        //////////////////////////////////////////////////////////////////////////

//        facade.deleteTrainee("john.black", "usain.bolt");

        log.info("======================= trainer =============================");
//        TrainerResponseDto trainer1 = facade.createTrainer(Data.TRAINER_TIGER_WOODS);
//        TrainerResponseDto trainer2 = facade.createTrainer(Data.TRAINER_TIGER_WOODS_NOT_EXISTING_SPECIALIZATION);
//        TrainerResponseDto trainer3 = facade.createTrainer(Data.TRAINER_TIGER_WOODS_NOT_VALID);
        ////////////////////////////////////////////////////////////////
//        TrainerResponseDto trainerByUsername = facade.findTrainerByUsername("tiger.woods", "usain.bolt");
//        // not found
//        facade.findTrainerByUsername("tiger.woods34", "usain.bolt");
        ///////////////////////////////////////////////////////////
//        TrainerDto updateTra = TrainerDto.builder()
//                .id(1L)
//                .firstName("new")
//                .lastName("new")
//                .specialization(1).build();
//
//        TrainerResponseDto trainer2 = facade.updateTrainer(updateTra, "usain.bolt");

        log.info("===================== training ==============================");
//        TrainingDto first = TrainingDto.builder()
//                .traineeId(2L)
//                .trainerId(1L)
//                .trainingType(1)
//                .date(LocalDate.of(2024, 6, 2))
//                .trainingName("second")
//                .duration(40)
//                .build();
//        TrainingResponseDto training = facade.addTraining(first, "usain.bolt");
        /////////////////////////////////////////////////////////
//        TrainingFilterDto filterDto = TrainingFilterDto.builder()
//                .owner("john.doe")
//                .startDate(LocalDate.of(2023,1,1))
//                .endDate(LocalDate.of(2024,7,1))
//                .trainingType("Cardio")
//                .partner("usain.bolt")
//                .build();
//        List<TrainingResponseDto> trainings = facade
//                .getAllTrainingsByUsernameAndFilter(filterDto, "usain.bolt");
//        log.info("Size " + trainings.size());
//        log.info(trainings.toString());
        log.info("================= update trainers ============================");
//        TraineeDto traineeDto = TraineeDto.builder()
//                .id(2L)
//                .build();
//        TrainerDto trainerDto = TrainerDto.builder()
//                .id(3L)
//                .build();
////
//        List<TrainerResponseDto> trainerResponseDtos =
//                facade.updateTrainers(traineeDto, List.of(trainerDto), "usain.bolt");
//        log.info("Size " + trainerResponseDtos.size());
//        log.info(trainerResponseDtos.toString());
        log.info("=============== not assigned trainers ====================");

//        List<TrainerResponseDto> trainers = facade
//                .getAllNotAssignedTrainersByTraineeUsername("john.doe","usain.bolt");
//        log.info("Size " + trainers.size());
//        log.info(trainers.toString());

    }
}
