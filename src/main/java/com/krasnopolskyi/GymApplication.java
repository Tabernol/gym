package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;


@Slf4j
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);


        TraineeService traineeService = context.getBean(TraineeService.class);
        UserService userService = context.getBean(UserService.class);

        TraineeDto traineeDto = TraineeDto.builder()
                .firstName("Maks")
                .lastName("Red")
                .address("new")
                .dateOfBirth(LocalDate.of(2000,1,1))
                .build();


        try {

            TraineeResponseDto traineeResponseDto = traineeService.save(traineeDto);
            System.out.println(traineeResponseDto);


//            Trainee trainee = traineeService.findById(1L);
//            System.out.println(trainee);
//            User user = userService.findById(1L);
//            System.out.println(user);


        } catch (EntityException e) {
            throw new RuntimeException(e);
        }


    }
}
