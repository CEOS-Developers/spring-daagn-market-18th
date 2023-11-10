package com.ceos18.springboot.user.service;

import com.ceos18.springboot.user.domain.User;
import com.ceos18.springboot.user.exception.AppException;
import com.ceos18.springboot.user.exception.ErrorCode;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String login(String email, String pwd){
        // user Email 없을 때
        User selectedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USEREMAIL_NOT_FOUND, "등록되지 않은 이메일입니다."));

        // password 잘못 되었을 때
        if(!pwd.equals(selectedUser.getPwd())){
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호입니다.");
        }

        return "token";
    }
}
