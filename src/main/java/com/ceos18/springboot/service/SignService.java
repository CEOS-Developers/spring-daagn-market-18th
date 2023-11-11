package com.ceos18.springboot.service;

import com.ceos18.springboot.entity.Member;
import com.ceos18.springboot.dto.signIn.request.SignInRequestDto;
import com.ceos18.springboot.dto.signIn.response.SignInResponseDto;
import com.ceos18.springboot.dto.signUp.request.SignUpRequestDto;
import com.ceos18.springboot.dto.signUp.response.SignUpResponseDto;
import com.ceos18.springboot.repository.MemberRepository;
import com.ceos18.springboot.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignService {
	private final MemberRepository memberRepository;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder encoder;

	@Transactional
	public SignUpResponseDto registMember(SignUpRequestDto request) {
		Member member = memberRepository.save(Member.from(request, encoder));
		try {
			memberRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
		}
		return SignUpResponseDto.from(member);
	}

	@Transactional(readOnly = true)
	public SignInResponseDto signIn(SignInRequestDto request) {
		Member member = memberRepository.findByAccount(request.getAccount())
				.filter(it -> encoder.matches(request.getPassword(), it.getPassword()))	// 암호화된 비밀번호와 비교하도록 수정
				.orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));


		String token = tokenProvider.createToken(String.format("%s:%s", member.getId(), member.getRole().name()));

		// email, refresh token, nickName
		return new SignInResponseDto(member.getId(), member.getEmail(), token, member.getRefreshToken(), member.getNickName(), member.getRole());
	}
}
