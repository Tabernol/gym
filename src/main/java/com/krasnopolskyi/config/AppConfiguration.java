package com.krasnopolskyi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.yaml")
@ComponentScan("com.krasnopolskyi")
@Slf4j
@EnableAspectJAutoProxy // enable AOP
public class AppConfiguration {

}
