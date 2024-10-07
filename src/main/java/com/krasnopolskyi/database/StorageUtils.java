package com.krasnopolskyi.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
@Slf4j
public class StorageUtils {
    private final ObjectMapper objectMapper;

    public StorageUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public <K, V> void saveToFile(String fileName, Map<K, V> data) {
        Path filePath = Path.of(fileName);

        try {
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                objectMapper.writeValue(writer, data);
            }

        } catch (IOException e) {
            log.error("Can't save to file " + fileName , e);
        }
    }

    // Deserialize JSON file to a map using Jackson
    public <K, V> Map<K, V> loadFromJsonFile(String fileName, Class<K> keyClass, Class<V> valueClass) throws IOException {
        try {
            return objectMapper.readValue(new File(fileName),
                    objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (IOException e) {
            throw new IOException("problem with file: " + fileName);
        }
    }
}
