package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UsernameGenerator usernameGenerator;

    @Override
    public User save(UserDto userDto) {

//        String login = usernameGenerator.generateUsername(userDto.getFirstName(), userDto.getLastName());
//        String password = PasswordGenerator.generatePassword();
//        User user = new User(id, userDto.getFirstName(), userDto.getLastName(), login, password, true);
//        log.info("Try save user");
//        User save = userRepository.save(user);
        log.info("User has been saved ");
        return new User();
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found user with id " + id));
    }

    @Override
    public User update(User user) {
        return userRepository.save(user).get();
    }

    @Override
    public boolean delete(User user) {
        log.info("attempt to delete user " + user.getId());
//        return userRepository.delete(user);
        return false;
    }
}
