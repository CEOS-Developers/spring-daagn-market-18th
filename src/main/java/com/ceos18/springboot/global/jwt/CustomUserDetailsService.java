package com.ceos18.springboot.global.jwt;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.member.exception.MemberErrorCode;
import com.ceos18.springboot.member.exception.MemberException;
import com.ceos18.springboot.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmailAndActivated(username, true)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND, username));

        return new CustomUserDetails(member);
    }
}
