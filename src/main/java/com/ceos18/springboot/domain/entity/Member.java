package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "members")
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String phoneNumber;

	@Column(unique = true)
	private String email;

	private String refreshToken;

	private String nickName;

	@Column(name="profile_image_url")
	private String profileImage;

	// 회원탈퇴 여부
	@ColumnDefault("false")
	private boolean isWithdrawal;

	private String mannerRating;

	private String region;

}
