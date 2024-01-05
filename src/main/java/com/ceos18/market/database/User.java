package com.ceos18.market.database;

import com.ceos18.market.database.base.BaseTimeEntity;
import com.ceos18.market.database.enums.Role;
import com.ceos18.market.database.enums.StatusCode;
import com.ceos18.market.domain.auth.dto.AuthRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "USR_LIST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "USR_NO", length = 12)
    @NotNull
    @Size(max = 12)
    private String userNo;

    @Column(name = "NIC_NM", length = 12, unique = true)
    @Size(max = 12, message = "닉네임은 12자 이하로 입력해주세요.")
    @NotNull
    private String nickName;

    @Column(length = 12, unique = true)
    @NotNull
    @Size(max = 12)
    private String phoneNumber;

    @Column(name = "IMG_URL", length = 200)
    @Size(max = 200)
    private String userImageUrl;

    @Column(name = "ADR", length = 50)
    @NotNull
    @Size(max = 50)
    private String address;

    @Column(name = "MNN_TEMP", columnDefinition = "CHAR(4)")
    @NotNull
    @ColumnDefault("'36.5'")
    @Size(max = 4)
    private String mannerTemperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADR_CERT_YN", columnDefinition = "CHAR(1)")
    @NotNull
    @ColumnDefault("'N'")
    private StatusCode addressCertificationYN;

    // 등록한 사용자 소프트웨어 식별 정보
    @Column(name = "UA", length = 50)
    @NotNull
    @Size(max = 50)
    private String userAgent;

    // 개인정보 동의
    @Enumerated(EnumType.STRING)
    @Column(name = "PRI_YN", columnDefinition = "CHAR(1)")
    @NotNull
    @ColumnDefault("'Y'")
    private StatusCode privacyYN;

    // Y: 마케팅 수신 동의, N: 마케팅 수신 미동의
    @Enumerated(EnumType.STRING)
    @Column(name = "MKTG_YN", columnDefinition = "CHAR(1)")
    @NotNull
    private StatusCode marketingYN;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String userNo, String nickName, String phoneNumber, String userImageUrl, String address,
                String userAgent, StatusCode marketingYN, Role role) {
        this.userNo = userNo;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.userImageUrl = userImageUrl;
        this.address = address;
        this.mannerTemperature = "36.5";
        this.addressCertificationYN = StatusCode.N;
        this.userAgent = userAgent;
        this.privacyYN = StatusCode.Y;
        this.marketingYN = marketingYN;
        this.role = role;
    }

    public static User of(AuthRequestDto authRequestDto) {
        return User.builder()
                .userNo(authRequestDto.getUserNo())
                .nickName(authRequestDto.getNickName())
                .phoneNumber(authRequestDto.getPhoneNumber())
                .userImageUrl(authRequestDto.getUserImageUrl())
                .address(authRequestDto.getAddress())
                .userAgent(authRequestDto.getUserAgent())
                .marketingYN(authRequestDto.getMarketingYN())
                .role(Role.ROLE_USER)
                .build();
    }

}
