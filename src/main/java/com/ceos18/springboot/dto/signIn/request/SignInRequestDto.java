package com.ceos18.springboot.dto.signIn.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDto {
	@Schema(description = "회원 아이디", example = "abc123")
	String account;
	@Schema(description = "회원 비밀번호", example = "1234")
	String password;
}
