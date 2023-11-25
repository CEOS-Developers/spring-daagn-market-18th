package com.ceos18.dangn.user.controller;

import com.ceos18.dangn.domain.User;
import com.ceos18.dangn.user.dto.SignInDto;
import com.ceos18.dangn.user.dto.SignInResponseDto;
import com.ceos18.dangn.user.dto.SignUpDto;
import com.ceos18.dangn.user.dto.UserProfileDto;
import com.ceos18.dangn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

    @GetMapping("/profile")
    public UserProfileDto getUserInfo(@AuthenticationPrincipal User user) {
        return userService.getUserProfile(user);
    }
}
