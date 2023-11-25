package com.daagn.clonestudy.post.dto.response;

import com.daagn.clonestudy.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WriterResponse {
    private Long id;
    private String nickname;
    private String town;
    private String icon;
    private Double temperature;

    public static WriterResponse fromEntity(Member member) {
        return WriterResponse.builder()
            .id(member.getId())
            .nickname(member.getNickname())
            .icon(member.getIcon())
            .town(member.getTown())
            .temperature(member.getTemperature())
            .build();
    }
}
