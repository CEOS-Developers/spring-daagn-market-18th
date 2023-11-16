package com.ceos18.springboot.user.controller;

import com.ceos18.springboot.user.dto.UserProfileImageDto;
import com.ceos18.springboot.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ceos18.springboot.user.service.UserService;
import com.ceos18.springboot.login.CurrentUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // 회원 프로필이미지 조회
    @GetMapping("/myProfileImage")
    public ResponseEntity<UserProfileImageDto> myProfileImage(@CurrentUser User user) {
        UserProfileImageDto imageDto = userService.getMyProfileImage(user);
        return ResponseEntity.ok(imageDto);
    }


}
