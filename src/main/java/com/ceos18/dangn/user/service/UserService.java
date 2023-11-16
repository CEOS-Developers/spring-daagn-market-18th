package com.ceos18.dangn.user.service;

import com.ceos18.dangn.config.JwtTokenProvider;
import com.ceos18.dangn.domain.Role;
import com.ceos18.dangn.domain.User;
import com.ceos18.dangn.exception.custom.InvalidPasswordException;
import com.ceos18.dangn.exception.custom.UserDuplicatedException;
import com.ceos18.dangn.exception.custom.UserNotFoundException;
import com.ceos18.dangn.user.dto.SignInDto;
import com.ceos18.dangn.user.dto.SignInResponseDto;
import com.ceos18.dangn.user.dto.SignUpDto;
import com.ceos18.dangn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<Void> signUp(SignUpDto signUpDto) {
        //중복체크
        userRepository.findByPhone(signUpDto.getPhone())
                .ifPresent(user -> {
                    throw new UserDuplicatedException();
                });

        //회원가입
        userRepository.save(User.builder()
                .phone(signUpDto.getPhone())
                .nickname(signUpDto.getNickname())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public SignInResponseDto signIn(SignInDto signInDto) {
        //전화번호 확인
        User user = userRepository.findByPhone(signInDto.getPhone())
                .orElseThrow(UserNotFoundException::new);

        //비밀번호 확인
        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        //TOKEN 발행
        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), signInDto.getPhone(), user.getRole().name());


        return SignInResponseDto.builder().accessToken(accessToken).build();
    }
}
