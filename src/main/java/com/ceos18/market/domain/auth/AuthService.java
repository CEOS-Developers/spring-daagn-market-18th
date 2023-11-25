package com.ceos18.market.domain.auth;

import com.ceos18.market.database.User;
import com.ceos18.market.domain.auth.dto.AuthRequestDto;
import com.ceos18.market.domain.auth.dto.SigninRequestDto;
import com.ceos18.market.global.jwt.TokenProvider;
import com.ceos18.market.global.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto signup(AuthRequestDto authRequestDto) {
        Optional<User> findUser = userRepository.findByPhoneNumber(authRequestDto.getPhoneNumber());
        if (findUser.isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        User user = User.of(authRequestDto);

        userRepository.save(user);

        SigninRequestDto signinRequestDto = SigninRequestDto.builder()
                .phoneNumber(user.getPhoneNumber())
                .build();

        return signin(signinRequestDto);
    }

    @Transactional
    public TokenDto signin(SigninRequestDto signinRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = signinRequestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.createAccessToken(authentication);
    }
}
