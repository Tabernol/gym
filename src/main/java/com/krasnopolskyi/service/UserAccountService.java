package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.entity.User;

public interface UserAccountService {

    boolean checkCredentials(UserCredentials credentials);

    boolean changePassword(User user, String password);

    boolean changeActivityStatus(User user);
}
