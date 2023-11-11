package com.ceos18.springboot.dto.signIn.response;

import com.ceos18.springboot.entity.enums.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInResponseDto {

	String email;
	String accessToken;
	String refreshToken;
	String nickName;
	@Schema(description = "회원 롤", example = "USER")
	MemberRole role;

	public SignInResponseDto(String email, String accessToken, String refreshToken, String nickName, MemberRole role) {
		this.email = email;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.nickName = nickName;
		this.role = role;
	}
}
