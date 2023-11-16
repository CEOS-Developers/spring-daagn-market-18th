package com.ceos18.springboot.user.service;

import com.ceos18.springboot.global.config.jwt.TokenProvider;
import com.ceos18.springboot.user.domain.User;
import com.ceos18.springboot.user.dto.request.UserLoginRequest;
import com.ceos18.springboot.user.dto.response.TokenResponse;
import com.ceos18.springboot.user.exception.AppException;
import com.ceos18.springboot.user.exception.ErrorCode;
import com.ceos18.springboot.user.helper.UserHelper;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHelper userHelper;
    private final TokenProvider tokenProvider;

    public TokenResponse login(UserLoginRequest request){
        String email = request.getEmail();
        String pwd = request.getPwd();

        final User selectedUser = userHelper.findByEmail(email);
        final Authentication authentication = userHelper.adminAuthorizationInput(selectedUser);

        // password 맞는지 확인하기
        userHelper.validatePwd(selectedUser, pwd);

        //access 토큰 생성
        String accessToken = tokenProvider.createAccessToken(selectedUser.getId(), selectedUser.getEmail(), authentication);

        return TokenResponse.from(accessToken);
    }
}
