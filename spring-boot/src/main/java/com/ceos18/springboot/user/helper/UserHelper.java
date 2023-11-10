package com.ceos18.springboot.user.helper;

import com.ceos18.springboot.global.config.user.PrincipalDetails;
import com.ceos18.springboot.global.config.user.PrincipalDetailsService;
import com.ceos18.springboot.user.domain.User;
import com.ceos18.springboot.user.exception.AppException;
import com.ceos18.springboot.user.exception.ErrorCode;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {
    public final UserRepository userRepository;
    private final PrincipalDetailsService principalDetailsService;

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new AppException(ErrorCode.USEREMAIL_NOT_FOUND, "존재하지 않는 이메일입니다."));
    }

    public void validatePwd(User selectedUser, String pwd) {
        if(!pwd.equals(selectedUser.getPwd())){
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호입니다.");
        }
    }

    public Authentication adminAuthorizationInput(User selectedUser) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(selectedUser.getEmail());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}