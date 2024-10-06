package com.krasnopolskyi.database.loaders;

import com.krasnopolskyi.database.loaders.parsers.csv.DataLoaderCsv;
import com.krasnopolskyi.database.loaders.parsers.json.DataLoaderJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoaderBeanPostProcessing implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(bean instanceof DataLoaderCsv){
            log.info("Load data from CSV file");
            ((DataLoaderCsv<?>) bean).loadData();
        }
        if(bean instanceof DataLoaderJson){
            log.info("Load data from JSON file");
            ((DataLoaderJson<?>) bean).loadData();
        }
        return bean;
    }
}
