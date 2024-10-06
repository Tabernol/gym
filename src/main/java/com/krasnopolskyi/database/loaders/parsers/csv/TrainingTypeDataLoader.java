package com.krasnopolskyi.database.loaders.parsers.csv;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.database.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TrainingTypeDataLoader implements DataLoaderCsv<TrainingType> {
    @Value("${data.path.training-type}")
    private String path;
    private final Storage storage;

    public TrainingTypeDataLoader(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void loadData() {
        List<TrainingType> trainingTypes = new ArrayList<>();
        String line;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found at path: " + path);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                // Skip the header line
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    int id = Integer.parseInt(values[0]);
                    String type = values[1];
                    trainingTypes.add(new TrainingType(id, type));
                }
                insertData(trainingTypes);
                log.info("From file: " + path);
                log.info("Inserted rows: " + trainingTypes.size());
            }

        } catch (FileNotFoundException e) {
            log.error("The specified file was not found: " + path, e);
        } catch (IOException e) {
            log.warn("Could not read from file: " + path, e);
        } catch (NumberFormatException ignored) {
            log.warn("Problem with parsing data: " + ignored.getMessage());
        }
    }


    private void insertData(List<TrainingType> trainingTypes) {
        trainingTypes.stream()
                .forEach(trainingType ->
                        storage.getTrainingTypes().put(trainingType.getId(), trainingType)
        );
    }
}
