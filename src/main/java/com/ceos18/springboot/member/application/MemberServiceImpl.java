package com.ceos18.springboot.member.application;

import com.ceos18.springboot.global.jwt.TokenProvider;
import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.member.dto.request.LoginMemberRequest;
import com.ceos18.springboot.member.dto.request.SignupMemberRequest;
import com.ceos18.springboot.member.dto.response.LoginMemberResponse;
import com.ceos18.springboot.member.dto.response.MemberResponse;
import com.ceos18.springboot.member.exception.MemberNotFoundException;
import com.ceos18.springboot.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(SignupMemberRequest signupMemberRequest) {

        Member member = signupMemberRequest.toEntity();
        member.encodePassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
    }

    public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginMemberRequest.getEmail(), loginMemberRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String token = tokenProvider.createAccessToken(authentication);

        return LoginMemberResponse.fromEntity(authentication.getName(), token);
    }

    public List<MemberResponse> getAllMembers() {

        List<Member> memberList = memberRepository.findAllByActivated(true);
        if (memberList.isEmpty()) {
            throw new MemberNotFoundException();
        }

        List<MemberResponse> memberResponseList = memberList.stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());

        return memberResponseList;
    }

    public MemberResponse getMember(Long id) {

        Member member = memberRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new MemberNotFoundException(id));

        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void deleteMember(Long id) {

        Member member = memberRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new MemberNotFoundException(id));

        member.updateActivatedFalse();
    }
}
