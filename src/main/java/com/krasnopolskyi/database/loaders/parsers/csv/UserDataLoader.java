package com.krasnopolskyi.database.loaders.parsers.csv;

import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.database.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserDataLoader implements DataLoaderCsv<User> {

    @Value("${data.path.user}")
    private String path;
    private final Storage storage;

    public UserDataLoader(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void loadData() {
        List<User> users = new ArrayList<>();
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
                    long id = Long.parseLong(values[0]);
                    String firstName = values[1];
                    String lastName = values[2];
                    String login = values[3];
                    String password = values[4];
                    boolean isActive = Boolean.parseBoolean(values[5]);
                    users.add(new User(id, firstName, lastName, login, password, isActive));
                }
                insertData(users);
                log.info("From file: " + path);
                log.info("Inserted rows " + users.size());
            }
        } catch (FileNotFoundException e) {
            log.error("The specified file was not found: " + path, e);
        } catch (IOException e) {
            log.warn("Could not read from file " + path, e);
        } catch (NumberFormatException ignored) {
            log.warn("Problem with parsing data " + ignored.getMessage(), ignored);
        }
    }

    private void insertData(List<User> users) {
        users.stream()
                .forEach(user ->
                        storage.getUsers().put(user.getId(), user)
                );
    }
}
