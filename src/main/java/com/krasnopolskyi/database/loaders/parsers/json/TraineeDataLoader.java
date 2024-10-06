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
    @Value("${data.path.trainee}")
    private String path;
    private final Storage storage;

    private final ObjectMapper objectMapper;

    public TraineeDataLoader(Storage storage, ObjectMapper objectMapper) {
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    @Override
    public void loadData() {
        List<Trainee> trainees;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found at path: " + path);
            }

            trainees = objectMapper.readValue(inputStream, new TypeReference<>() {});

            insertData(trainees);
            log.info("From file: " + path);
            log.info("Inserted rows: " + trainees.size());

        } catch (FileNotFoundException e) {
            log.error("The specified file was not found: " + path, e);
        } catch (IOException e) {
            log.warn("Could not read from file: " + path, e);
        }
    }

    private void insertData(List<Trainee> trainees) {
        trainees.stream()
                .forEach(trainee ->
                        storage.getTrainees().put(trainee.getId(), trainee)
                );
    }
}
