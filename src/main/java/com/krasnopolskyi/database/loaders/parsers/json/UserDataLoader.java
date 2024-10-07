package com.krasnopolskyi.database.loaders.parsers.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class UserDataLoader implements DataLoaderJson<User>{

    @Value("${data.load.users}")
    private String usersPath;
    private final Storage storage;
    private final ObjectMapper objectMapper;

    public UserDataLoader(Storage storage, ObjectMapper objectMapper) {
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    @Override
    public void loadData() {
        List<User> users;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(usersPath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found at path: " + usersPath);
            }

            users = objectMapper.readValue(inputStream, new TypeReference<>() {});

            insertData(users);
            log.info("From file: " + usersPath);
            log.info("Inserted rows: " + users.size());

        } catch (FileNotFoundException e) {
            log.error("The specified file was not found: " + usersPath, e);
        } catch (IOException e) {
            log.warn("Could not read from file: " + usersPath, e);
        }
    }

    private void insertData(List<User> users) {
        users.stream()
                .forEach(user ->
                        storage.getUsers().put(user.getId(), user)
                );
    }
}
