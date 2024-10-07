package com.krasnopolskyi.database.loaders.parsers.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class TrainerDataLoader implements DataLoaderJson<Trainer> {
    @Value("${data.save.trainers}")
    private String trainersPath;

    private final Storage storage;
    private final ObjectMapper objectMapper;

    public TrainerDataLoader(Storage storage, ObjectMapper objectMapper) {
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    @Override
    public void loadData() {
        List<Trainer> trainers;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(trainersPath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found at path: " + trainersPath);
            }

            trainers = objectMapper.readValue(inputStream, new TypeReference<>() {});

            insertData(trainers);
            log.info("From file: " + trainersPath);
            log.info("Inserted rows: " + trainers.size());

        } catch (FileNotFoundException e) {
            log.error("The specified file was not found: " + trainersPath, e);
        } catch (IOException e) {
            log.warn("Could not read from file: " + trainersPath, e);
        }
    }

    private void insertData(List<Trainer> trainers) {
        trainers.stream()
                .forEach(trainer ->
                        storage.getTrainers().put(trainer.getId(), trainer)
                );
    }
}
