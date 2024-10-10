package com.krasnopolskyi.utils;

import com.krasnopolskyi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameGenerator {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateUsername(String firstName, String lastName){
        int count = 1;
        String template = firstName + "." + lastName;
        String username = template;
        while (isUsernameExist(username)){
            username = template + count;
            count++;
        }
        return username;
    }

    private boolean isUsernameExist(String username){
        return userRepository.isUsernameExist(username);
    }
}
