package com.krasnopolskyi.aop;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.database.StorageUtils;
import com.krasnopolskyi.database.dao.*;
import com.krasnopolskyi.entity.*;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileOperationAspectTest {

    @Mock
    private StorageUtils storageUtils;

    @Mock
    private Storage storage;

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
