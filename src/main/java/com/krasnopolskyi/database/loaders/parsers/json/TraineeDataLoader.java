package com.krasnopolskyi.database.loaders.parsers.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class TraineeDataLoader implements DataLoaderJson<Trainee> {
    @Value("${data.load.trainees}")
    private String traineesPath;
    private final Storage storage;
    private final ObjectMapper objectMapper;

    public TraineeDataLoader(Storage storage, ObjectMapper objectMapper) {
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    @Override
    public void loadData() {
        List<Trainee> trainees;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(traineesPath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found at path: " + traineesPath);
            }

            trainees = objectMapper.readValue(inputStream, new TypeReference<>() {});

            insertData(trainees);
            log.info("From file: " + traineesPath);
            log.info("Inserted rows: " + trainees.size());

        } catch (FileNotFoundException e) {
            log.error("The specified file was not found: " + traineesPath, e);
        } catch (IOException e) {
            log.warn("Could not read from file: " + traineesPath, e);
        }
    }

    private void insertData(List<Trainee> trainees) {
        trainees.stream()
                .forEach(trainee ->
                        storage.getTrainees().put(trainee.getId(), trainee)
                );
    }
}
