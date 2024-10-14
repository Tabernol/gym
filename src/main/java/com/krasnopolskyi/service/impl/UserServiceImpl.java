package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.PasswordGenerator;
import com.krasnopolskyi.utils.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UsernameGenerator usernameGenerator;

    @Override
    public User create(UserDto userDto) throws ValidateException {
        String username = usernameGenerator.generateUsername(userDto.firstName(), userDto.lastName());
        String password = PasswordGenerator.generatePassword();
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setIsActive(true);
        log.debug("User has been created " + user);
        return user;
    }

    @Override
    public User findById(Long id) throws EntityException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found user with id " + id));
    }

    @Override
    @Transactional
    public boolean checkCredentials(UserCredentials credentials) throws EntityException {
        User user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new EntityException("Could not found user: " + credentials.username()));

        if (user.getPassword().equals(credentials.password())) {
            return true;
        }
        return false;
    }

    @Override
    public User changePassword(User user, String password) throws GymException {
        // authorization
        user.setPassword(password);
        return userRepository.update(user);
    }

    @Override
    public User changeActivityStatus(User user) throws GymException {
        user.setIsActive(!user.getIsActive()); //status changes here
        return userRepository.update(user);
    }
}
