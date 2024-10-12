package com.krasnopolskyi.aop;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileOperationAspectTest {

    @InjectMocks
    private FileOperationAspect fileOperationAspect;

    private JoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);
        joinPoint = mock(JoinPoint.class);
    }

    @Test
    void testAfterRepositoryOperation_UnexpectedRepository() {
        // Mock an unexpected repository
        Object unexpectedRepository = new Object();
        when(joinPoint.getTarget()).thenReturn(unexpectedRepository);

        // Expecting IllegalStateException for unexpected repository
        assertThrows(IllegalStateException.class, () -> fileOperationAspect.afterRepositoryOperation(joinPoint));
    }
}
