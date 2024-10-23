package com.krasnopolskyi.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class HibernateConfigTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // Create a new application context with the HibernateConfig class
        applicationContext = new AnnotationConfigApplicationContext(HibernateConfig.class);
    }

    @Test
    void testDataSourceBeanCreation() {
        // Assert that the DataSource bean exists
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        assertNotNull(dataSource);
        assertTrue(dataSource instanceof HikariDataSource);

        // Assert that the HikariDataSource is correctly configured
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        assertNotNull(hikariDataSource.getJdbcUrl());
        assertNotNull(hikariDataSource.getUsername());
        assertNotNull(hikariDataSource.getPassword());
    }

    @Test
    void testLiquibaseBeanCreation() {
        // Assert that the SpringLiquibase bean exists
        SpringLiquibase liquibase = applicationContext.getBean(SpringLiquibase.class);
        assertNotNull(liquibase);

        // Assert that the changeLog is correctly set
        assertNotNull(liquibase.getChangeLog());
    }

    @Test
    void testSessionFactoryBeanCreation() {
        // Assert that the sessionFactory bean exists
        SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);
        assertNotNull(sessionFactory);
    }

    @Test
    void testTransactionManagerBeanCreation() {
        // Assert that the transactionManager bean exists
        HibernateTransactionManager transactionManager = applicationContext.getBean(HibernateTransactionManager.class);
        assertNotNull(transactionManager);

        // Assert that the sessionFactory is correctly set in the transaction manager
        assertNotNull(transactionManager.getSessionFactory());
    }
}
