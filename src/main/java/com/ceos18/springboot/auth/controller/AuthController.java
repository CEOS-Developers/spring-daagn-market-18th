package com.ceos18.springboot.auth.controller;

import com.ceos18.springboot.auth.service.AuthService;
import com.ceos18.springboot.common.dto.NormalResponseDto;
import com.ceos18.springboot.common.dto.TokenDto;
import com.ceos18.springboot.login.dto.LoginRequestDto;
import com.ceos18.springboot.user.dto.UserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final long COOKIE_EXPIRATION = 7776000; // 90일

    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<NormalResponseDto> join(@RequestBody @Valid UserRequestDto requestDto) {
        authService.joinMember(requestDto);
        return ResponseEntity.ok(NormalResponseDto.success());
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {

        // authService.login을 통해 로그인 시도 및 토큰 발급
        TokenDto tokenDto = authService.login(loginRequest);

        // 리프레시 토큰을 HttpCookie로 만들어 응답에 추가
        HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
                .maxAge(COOKIE_EXPIRATION)
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())    //서드파티 쿠키 사용 허용
                .build();

        // 액세스 토큰은 HttpHeaders.AUTHORIZATION 헤더에 추가하여 응답
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .build();
    }

}