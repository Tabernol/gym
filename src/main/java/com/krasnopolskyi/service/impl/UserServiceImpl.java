package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.PasswordGenerator;
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
    public User create(UserDto userDto) throws ValidateException {
        String username = usernameGenerator.generateUsername(userDto.firstName(), userDto.lastName());
        String password = PasswordGenerator.generatePassword();
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setIsActive(true);
        log.info("User has been created " + user);
        return user;
    }

    @Override
    public User findById(Long id) throws EntityException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found user with id " + id));
    }

    @Override
    public User update(User user) {
        return user;
//        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
//        log.debug("attempt to delete user " + user.getId());
//        return userRepository.delete(user);
        return false;
    }
}
