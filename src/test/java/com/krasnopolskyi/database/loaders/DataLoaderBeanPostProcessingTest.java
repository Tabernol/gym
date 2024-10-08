package com.krasnopolskyi.database.loaders;

import com.krasnopolskyi.database.loaders.parsers.csv.DataLoaderCsv;
import com.krasnopolskyi.database.loaders.parsers.json.DataLoaderJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class DataLoaderBeanPostProcessingTest {

    @Mock
    private DataLoaderCsv<?> dataLoaderCsv;

    @Mock
    private DataLoaderJson<?> dataLoaderJson;

    @InjectMocks
    private DataLoaderBeanPostProcessing beanPostProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostProcessAfterInitialization_forCsvLoader() throws BeansException {
        // Call the method with a DataLoaderCsv instance
        beanPostProcessor.postProcessAfterInitialization(dataLoaderCsv, "dataLoaderCsv");

        // Verify that loadData() was called on DataLoaderCsv
        verify(dataLoaderCsv, times(1)).loadData();
        verifyNoInteractions(dataLoaderJson); // Ensure JSON loader was not called
    }

    @Test
    void testPostProcessAfterInitialization_forJsonLoader() throws BeansException {
        // Call the method with a DataLoaderJson instance
        beanPostProcessor.postProcessAfterInitialization(dataLoaderJson, "dataLoaderJson");

        // Verify that loadData() was called on DataLoaderJson
        verify(dataLoaderJson, times(1)).loadData();
        verifyNoInteractions(dataLoaderCsv); // Ensure CSV loader was not called
    }

    @Test
    void testPostProcessAfterInitialization_forNonLoaderBean() throws BeansException {
        // Call the method with a non-DataLoader bean (e.g., String bean)
        Object nonLoaderBean = new Object();
        Object result = beanPostProcessor.postProcessAfterInitialization(nonLoaderBean, "nonLoaderBean");

        // Ensure that neither CSV nor JSON loader was called
        verifyNoInteractions(dataLoaderCsv);
        verifyNoInteractions(dataLoaderJson);

        // Ensure the bean is returned unchanged
        assertSame(nonLoaderBean, result);
    }
}
