package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);


        TraineeService traineeService = context.getBean(TraineeService.class);
        UserService userService = context.getBean(UserService.class);


        try {
            Trainee trainee = traineeService.findById(1L);
            System.out.println(trainee);
//            User user = userService.findById(1L);
//            System.out.println(user);


        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
