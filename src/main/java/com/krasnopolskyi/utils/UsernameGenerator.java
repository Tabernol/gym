package com.krasnopolskyi.utils;

import com.krasnopolskyi.database.dao.UserRepository;
import com.krasnopolskyi.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameGenerator {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateUsername(String firstName, String lastName) throws ValidateException {
        if(firstName.length() < 2 || lastName.length() < 2){
            throw new ValidateException("First name or Last name must consist of at least two letters");
        }
        int count = 1;
        String template = firstName.toLowerCase() + "." + lastName.toLowerCase();
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
