package com.daagn.clonestudy.member.service;

import com.daagn.clonestudy.auth.JwtTokenProvider;
import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import com.daagn.clonestudy.member.dto.request.JoinRequest;
import com.daagn.clonestudy.member.dto.request.LoginRequest;
import com.daagn.clonestudy.member.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinRequest joinRequest) {
        validateDuplication(joinRequest);
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        Member member = joinRequest.toEntity(encodedPassword);
        memberRepository.save(member);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
            .orElseThrow(() -> new IllegalArgumentException("전화번호로 회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("회원정보의 비밀번호와 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getPhoneNumber(), member.getRoles());
        return LoginResponse.builder()
            .accessToken(token)
            .build();
    }

    private void validateDuplication(JoinRequest joinRequest) {
        if (memberRepository.findByPhoneNumber(joinRequest.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("중복되는 전화번호입니다.");
        }
    }
}
