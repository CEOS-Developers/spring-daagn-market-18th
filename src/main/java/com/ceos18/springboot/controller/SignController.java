package com.ceos18.springboot.controller;

import com.ceos18.springboot.dto.signIn.request.SignInRequestDto;
import com.ceos18.springboot.dto.signIn.response.SignInResponseDto;
import com.ceos18.springboot.dto.signUp.request.SignUpRequestDto;
import com.ceos18.springboot.dto.signUp.response.SignUpResponseDto;
import com.ceos18.springboot.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 가입 및 로그인")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignController {
	private final SignService signService;

	@Operation(summary = "회원 가입")
	@PostMapping("/sign-up")
	public SignUpResponseDto signUp(@RequestBody SignUpRequestDto request) {
		return signService.registMember(request);
	}

	@Operation(summary = "로그인")
	@PostMapping("/sign-in")
	public SignInResponseDto signIn(@RequestBody SignInRequestDto request) {
		return signService.signIn(request);
	}
}