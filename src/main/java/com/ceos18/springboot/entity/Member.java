package com.ceos18.springboot.entity;

import com.ceos18.springboot.entity.base.BaseTimeEntity;
import com.ceos18.springboot.entity.enums.MemberRole;
import com.ceos18.springboot.dto.signUp.request.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Entity
@Table(name = "members")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String account;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

//	@Column(nullable = false, unique = true, length = 20)
	private String phoneNumber;

	@Column(unique = true)
	private String email;

	private String refreshToken;

	private String nickName;

	@Column(name="profile_image_url")
	private String profileImage;

	// 회원탈퇴 여부
	@ColumnDefault("false")
//	@Column(nullable = false)
	private boolean isWithdrawal;

//	@Column(nullable = false)
	private BigDecimal mannerRating;

//	@Column(nullable = false)
	private String region;

	@Builder
	public Member(String phoneNumber, String email, String nickName, String profileImage, boolean isWithdrawal, BigDecimal mannerRating, String region) {
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.nickName = nickName;
		this.profileImage = profileImage;
		this.isWithdrawal = isWithdrawal;
		this.mannerRating = mannerRating;
		this.region = region;
	}

	// SignUpRequestDto -> Member 생성
	// 일부 필드 빠져있음
	public static Member from(SignUpRequestDto request, PasswordEncoder encoder) {
		return Member.builder()
				.account(request.getAccount())
				.password(encoder.encode(request.getPassword()))
				.email(request.getEmail())
				.nickName(request.getNickName())
				.role(MemberRole.USER) // 회원가입시 USER로 고정
				.build();
	}

}
