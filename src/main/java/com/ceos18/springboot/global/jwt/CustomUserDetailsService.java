package com.ceos18.springboot.global.jwt;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.member.exception.MemberNotFoundException;
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
                .orElseThrow(() -> new MemberNotFoundException(username));

        return new CustomUserDetails(member);
    }
}
