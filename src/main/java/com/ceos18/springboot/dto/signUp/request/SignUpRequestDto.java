package com.ceos18.springboot.dto.signUp.request;

import com.ceos18.springboot.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
	@Schema(description = "회원 아이디", example = "abc123")
	String account;
	@Schema(description = "회원 비밀번호", example = "1234")
	String password;
	@Schema(description = "회원 이메일", example = "abc@gmail.com")
	String email;
	@Schema(description = "회원 닉네임", example = "별명")
	String nickName;
}
