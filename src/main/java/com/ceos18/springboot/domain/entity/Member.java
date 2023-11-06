package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

	@Column(nullable = false, unique = true, length = 20)
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

}
