package com.ceos18.springboot.dto.signUp.response;

import com.ceos18.springboot.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {

	@Schema(description = "회원 아이디", example = "1")
	Long id;
	@Schema(description = "회원 아이디", example = "abc123")
	String account;
	@Schema(description = "회원 이메일", example = "abc@gmail.com")
	String email;
	@Schema(description = "회원 닉네임", example = "별명")
	String nickName;

	public static SignUpResponseDto from(Member member) {
		SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
		signUpResponseDto.setId(member.getId());
		signUpResponseDto.setAccount(member.getAccount());
		signUpResponseDto.setEmail(member.getEmail());
		signUpResponseDto.setNickName(member.getNickName());
		return signUpResponseDto;
	}
}
