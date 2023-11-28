package com.ceos18.springboot.user;

import com.ceos18.springboot.user.dto.request.UserJoinRequest;
import com.ceos18.springboot.user.dto.request.UserLoginRequest;
import com.ceos18.springboot.user.dto.response.TokenResponse;
import com.ceos18.springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){
        userService.join(dto);
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request){

        //로그인 시 TokenResponse return
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(request));
    }
}
