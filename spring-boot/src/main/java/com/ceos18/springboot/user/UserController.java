package com.ceos18.springboot.user;

import com.ceos18.springboot.user.dto.request.UserLoginRequest;
import com.ceos18.springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request){
        String token = userService.login(request.getEmail(), request.getPwd());
        //로그인 시 토큰 리턴
        return ResponseEntity.ok().body(token);
    }
}
