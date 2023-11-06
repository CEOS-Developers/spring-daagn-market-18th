package com.ceos18.springboot.member.dto.response;

import com.ceos18.springboot.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private final Long id;

    private final String password;

    private final String nickname;

    private final String phone;

    private final Double temperature;

    private final String email;

    private final String imageUrl;

    private final Boolean activated;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Builder
    public MemberResponse(Long id, String password, String nickname, String phone, Double temperature, String email,
                          String imageUrl, Boolean activated, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.temperature = temperature;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .temperature(member.getTemperature())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .activated(member.getActivated())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdateAt())
                .build();
    }
}
