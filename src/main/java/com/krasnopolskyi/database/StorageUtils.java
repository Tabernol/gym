package com.krasnopolskyi.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class StorageUtils {
    private final ObjectMapper objectMapper;

    public StorageUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Serialize map data to a JSON file using Jackson
    public <K, V> void saveToJsonFile(Map<K, V> map, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), map);
    }

    // Deserialize JSON file to a map using Jackson
    public <K, V> Map<K, V> loadFromJsonFile(String fileName, Class<K> keyClass, Class<V> valueClass) throws IOException {
        return objectMapper.readValue(new File(fileName),
                objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
    }
}
