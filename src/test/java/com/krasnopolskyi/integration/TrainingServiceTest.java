//package com.krasnopolskyi.integration;
//
//
//import com.krasnopolskyi.config.HibernateConfig;
//import com.krasnopolskyi.config.ValidatorConfig;
//import com.krasnopolskyi.dto.request.TraineeDto;
//import com.krasnopolskyi.dto.request.UserCredentials;
//import com.krasnopolskyi.dto.response.TraineeResponseDto;
//import com.krasnopolskyi.entity.*;
//import com.krasnopolskyi.exception.EntityException;
//import com.krasnopolskyi.exception.GymException;
//import com.krasnopolskyi.facade.MainFacade;
//import com.krasnopolskyi.repository.*;
//import com.krasnopolskyi.repository.impl.TraineeRepositoryImpl;
//import com.krasnopolskyi.repository.impl.UserRepositoryImpl;
//import com.krasnopolskyi.security.AuthenticationManager;
//import com.krasnopolskyi.service.TraineeService;
//import com.krasnopolskyi.service.TrainerService;
//import com.krasnopolskyi.service.TrainingService;
//import com.krasnopolskyi.service.UserService;
//import com.krasnopolskyi.service.impl.TraineeServiceImpl;
//import com.krasnopolskyi.service.impl.UserServiceImpl;
//import com.krasnopolskyi.utils.UsernameGenerator;
//import com.krasnopolskyi.validation.CommonValidator;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import liquibase.integration.spring.SpringLiquibase;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
//import org.hibernate.cfg.Environment;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@Testcontainers
//@EnableTransactionManagement
//@ContextConfiguration(classes = {HibernateConfig.class, ValidatorConfig.class})
//@PropertySource("classpath:application.yaml")
//public class TrainingServiceTest {
//    private static DataSource dataSource;
//    private static SessionFactory sessionFactory;
//
//    private static HibernateTransactionManager transactionManager;
//
//    private static TraineeRepository traineeRepository;
//    private static TrainerRepository trainerRepository;
//    private static TrainingRepository trainingRepository;
//    private static TrainingTypeRepository trainingTypeRepository;
//    private static UserRepository userRepository;
//
//    private static TraineeService traineeService;
//    private static TrainerService trainerService;
//    private static TrainingService trainingService;
//    private static CommonValidator commonValidator;
//    private static UserService userService;
//
//
//    private static MainFacade facade;
//    private static AuthenticationManager manager;
//
//    @Container
//    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
//    @BeforeAll
//    public static void setup() {
//        postgresContainer.start();
//
////        // Configure HikariCP DataSource
////        HikariConfig hikariConfig = new HikariConfig();
////        hikariConfig.setJdbcUrl(postgresContainer.getJdbcUrl());
////        hikariConfig.setUsername(postgresContainer.getUsername());
////        hikariConfig.setPassword(postgresContainer.getPassword());
////        hikariConfig.setDriverClassName("org.postgresql.Driver");
////        dataSource = new HikariDataSource(hikariConfig);
//
////        // Set up Hibernate SessionFactory
////        sessionFactory = sessionFactory(dataSource);
////
////        transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(sessionFactory);
////
////        applyLiquibaseMigrations();
//
//        // init repositories
//        traineeRepository = new TraineeRepositoryImpl(sessionFactory);
//        userRepository = new UserRepositoryImpl(sessionFactory);
//
//
//        userService = new UserServiceImpl(userRepository, new UsernameGenerator(userRepository));
//        traineeService = new TraineeServiceImpl(traineeRepository, trainerRepository, userService);
//
//
//
//        manager = new AuthenticationManager(userService);
//        facade = new MainFacade(
//                traineeService,
//                trainerService,
//                trainingService,
//                commonValidator,
//                manager,
//                userService);
//
//
//    }
//
//
////    public static SessionFactory sessionFactory(DataSource dataSource) {
////        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
////        sessionFactoryBean.setDataSource(dataSource);
////        sessionFactoryBean.setPackagesToScan("com.krasnopolskyi");
////        sessionFactoryBean.setHibernateProperties(hibernateProperties());
////        try {
////            // This step ensures proper initialization
////            sessionFactoryBean.afterPropertiesSet();
////        } catch (Exception e) {
////            e.printStackTrace();
////            throw new RuntimeException("Failed to initialize sessionFactory", e);
////        }
////
////        // Now you can safely retrieve the SessionFactory
////        return sessionFactoryBean.getObject();
////    }
////
////    private static Properties hibernateProperties() {
////        Properties properties = new Properties();
////
////        properties.put(Environment.DRIVER, "org.postgresql.Driver");
////        properties.put(Environment.URL, postgresContainer.getJdbcUrl());
////        properties.put(Environment.USER, postgresContainer.getUsername());
////        properties.put(Environment.PASS, postgresContainer.getPassword());
////        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
////        properties.put(Environment.SHOW_SQL, "true");
////        properties.put(Environment.FORMAT_SQL, "true");
////        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");
////        properties.put(Environment.PHYSICAL_NAMING_STRATEGY, new CamelCaseToUnderscoresNamingStrategy());
////
////        return properties;
////    }
//
////    private static SessionFactory createSessionFactory() {
////        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
////
////        // Hibernate settings equivalent to hibernate.cfg.xml's properties
////        Properties settings = new Properties();
////        settings.put(Environment.DRIVER, "org.postgresql.Driver");
////        settings.put(Environment.URL, postgresContainer.getJdbcUrl());
////        settings.put(Environment.USER, postgresContainer.getUsername());
////        settings.put(Environment.PASS, postgresContainer.getPassword());
////        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
////        settings.put(Environment.SHOW_SQL, "true");
////        settings.put(Environment.FORMAT_SQL, "true");
////        //settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
////
////        configuration.setProperties(settings);
////        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
////        configuration.addAnnotatedClass(Trainee.class);
////        configuration.addAnnotatedClass(Trainer.class);
////        configuration.addAnnotatedClass(User.class);
////        configuration.addAnnotatedClass(TrainingType.class);
////        configuration.addAnnotatedClass(Training.class);
////
////        return configuration.buildSessionFactory();
////    }
//
////    private static void applyLiquibaseMigrations() {
////        SpringLiquibase liquibase = new SpringLiquibase();
////        liquibase.setDataSource(dataSource);
////        liquibase.setChangeLog("classpath:db/changelog/master.yaml");  // Update with your actual Liquibase changelog file path
////        try {
////            liquibase.afterPropertiesSet();  // Apply the migrations
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    @Test
//    public void testDataSourceIsInitialized() {
//        assertNotNull(dataSource);
//        assertNotNull(sessionFactory);
//        assertNotNull(transactionManager);
//
//
//    }
//
////    @Test
////    @Transactional
////    public void createTraineeTest() throws GymException {
////            TraineeDto traineeDto = TraineeDto.builder()
////                    .firstName("test")
////                    .lastName("test")
////                    .build();
////            TraineeResponseDto responseDto = traineeService.save(traineeDto);
////            assertEquals(responseDto.firstName(), "test");
////    }
//
//    @Test
//    public void createTraineeTest() throws GymException {
////        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        try {
//            TraineeDto traineeDto = TraineeDto.builder()
//                    .firstName("test")
//                    .lastName("test")
//                    .build();
//            TraineeResponseDto responseDto = traineeService.save(traineeDto);
//            assertEquals(responseDto.firstName(), "test");
//
////            transactionManager.commit(status);
//        } catch (Exception e) {
////            transactionManager.rollback(status);
//            throw e;
//        }
//        session.getTransaction().commit();
//    }
//
//
//
//
//    @AfterAll
//    public static void tearDown() {
//        sessionFactory.close();
//        postgresContainer.stop();
//    }
//}
