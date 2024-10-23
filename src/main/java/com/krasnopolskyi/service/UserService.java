package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(UserDto userDto) {
        String username = generateUsername(userDto.firstName(), userDto.lastName());
        String password = PasswordGenerator.generatePassword();
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setIsActive(true);
        user.setRole(userDto.role());
        return user;
    }

    public User findById(Long id) throws EntityException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found user with id " + id));
    }

    private User findByUsername(String username) throws EntityException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityException("Could not found user: " + username));
    }


    @Transactional
    public boolean checkCredentials(UserCredentials credentials) throws EntityException {
        User user = findByUsername(credentials.username());

        if (user.getPassword().equals(credentials.password())) {
            return true;
        }
        return false;
    }


    @Transactional
    public User changePassword(String username, String password) throws GymException {
        User user = findByUsername(username);
        user.setPassword(password);
        return userRepository.update(user);
    }


    @Transactional
    public User changeActivityStatus(String target) throws GymException {
        User user = findByUsername(target);
        user.setIsActive(!user.getIsActive()); //status changes here
        return userRepository.update(user);
    }

    private String generateUsername(String firstName, String lastName) {
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
