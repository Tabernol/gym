package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.UserRepository;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.IdGenerator;
import com.krasnopolskyi.utils.PasswordGenerator;
import com.krasnopolskyi.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    // initialized via autowired because task condition 4
    // I prefer initialized via constructor
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsernameGenerator usernameGenerator;

    @Override
    public User save(UserDto userDto) {
        long id = IdGenerator.generateId();
        String login = usernameGenerator.generateUsername(userDto.getFirstName(), userDto.getLastName());
        String password = PasswordGenerator.generatePassword();
        User user = new User(id, userDto.getFirstName(), userDto.getLastName(), login, password, true);
        System.out.println("user in userservice " + user);
        return userRepository.save(user);
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
        return userRepository.delete(user);
    }
}
