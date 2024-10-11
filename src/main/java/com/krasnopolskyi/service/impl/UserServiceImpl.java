package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.UserRepository;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.IdGenerator;
import com.krasnopolskyi.utils.PasswordGenerator;
import com.krasnopolskyi.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    // initialized via autowired because task condition 4
    // I prefer initialized via constructor
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsernameGenerator usernameGenerator;

    @Override
    public User save(UserDto userDto) throws ValidateException {
        long id = IdGenerator.generateId();
        // can throw ValidateException
        String username = usernameGenerator
                .generateUsername(userDto.getFirstName(), userDto.getLastName());
        String password = PasswordGenerator.generatePassword();
        User user = User.builder()
                .id(id)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();
        User save = userRepository.save(user);
        log.debug("User has been saved " + save.getId());
        return save;
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found user with id " + id));
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
        log.debug("attempt to delete user " + user.getId());
        return userRepository.delete(user);
    }
}
