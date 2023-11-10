package com.ceos18.springboot.user.service;

import com.ceos18.springboot.user.domain.User;
import com.ceos18.springboot.user.exception.AppException;
import com.ceos18.springboot.user.exception.ErrorCode;
import com.ceos18.springboot.user.helper.UserHelper;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserHelper userHelper;

    public String login(String email, String pwd){
        // user Email 없을 때
        User selectedUser = userHelper.findByEmail(email);

        // password 잘못 되었을 때
        userHelper.validatePwd(selectedUser, pwd);

        return "token";
    }
}
