package com.ceos18.springboot.auth.service;

import com.ceos18.springboot.common.dto.TokenDto;
import com.ceos18.springboot.exception.ErrorCode;
import com.ceos18.springboot.exception.KarrotException;
import com.ceos18.springboot.jwt.JwtTokenProvider;
import com.ceos18.springboot.login.dto.LoginRequestDto;
import com.ceos18.springboot.user.dto.UserRequestDto;
import com.ceos18.springboot.user.entity.User;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String SERVER = "Server";

    //회원가입
    @Transactional
    public void joinMember(UserRequestDto requestDto) {
        if (findUserByEmail(requestDto.getEmail()))
            throw new KarrotException(ErrorCode.ALREADY_MEMBER);

        User user = requestDto.toMember(passwordEncoder); // 비밀번호를 인코딩하여 저장
        userRepository.save(user);
    }

    //로그인
    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return generateToken(SERVER, authentication.getName(), getAuthorities(authentication));
    }

    // 토큰 발급
    @Transactional
    public TokenDto generateToken(String provider, String email, String authorities) {

        TokenDto authToken = jwtTokenProvider.createToken(email, authorities);

        return authToken;
    }

    // 권한 이름 가져오기
    public String getAuthorities(Authentication authentication) {

        // 권한 이름들을 ","로 구분하여 하나의 문자열로 변환하기
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // AT로부터 principal 추출
    public String getPrincipal(String requestAccessToken) {
        return jwtTokenProvider.getAuthentication(requestAccessToken).getName();
    }

    // "Bearer {AT}"에서 {AT} 추출
    public String resolveToken(String requestAccessTokenInHeader) {
        if (requestAccessTokenInHeader != null && requestAccessTokenInHeader.startsWith("Bearer ")) {
            return requestAccessTokenInHeader.substring(7);
        }
        return null;
    }

    public boolean findUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public boolean findMemberByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }



}