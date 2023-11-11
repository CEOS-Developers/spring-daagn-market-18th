package com.ceos18.springboot.dto.signIn.response;

import jakarta.persistence.Column;
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

	public SignInResponseDto(String email, String accessToken, String refreshToken, String nickName) {
		this.email = email;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.nickName = nickName;
	}
}
