package com.ceos.daangnmarket.domain.auth.service;

import com.ceos.daangnmarket.common.jwt.JwtAuthenticationFilter;
import com.ceos.daangnmarket.common.jwt.TokenProvider;
import com.ceos.daangnmarket.domain.auth.dto.SignInDto;
import com.ceos.daangnmarket.domain.auth.dto.SignUpDto;
import com.ceos.daangnmarket.domain.auth.dto.TokenDto;
import com.ceos.daangnmarket.domain.user.entity.User;
import com.ceos.daangnmarket.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private TokenProvider tokenProvider;

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Transactional
  public User signUp(SignUpDto signUpDto) {
    if (userRepository.findByPhoneNumber(signUpDto.getPhoneNumber()).orElse(null) != null) {
      throw new RuntimeException("이미 가입되어 있는 유저입니다.");
    }

    User user = User.builder()
      .nickname(signUpDto.getNickname())
      .password(passwordEncoder.encode(signUpDto.getPassword()))
      .phoneNumber(signUpDto.getPhoneNumber())
      .build();

    return userRepository.save(user);
  }

  @Transactional
  public TokenDto signIn(SignInDto signInDto) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(signInDto.getNickname(), signInDto.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

    return new TokenDto(jwt);
  }
}
