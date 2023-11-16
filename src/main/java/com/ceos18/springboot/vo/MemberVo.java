package com.ceos18.springboot.vo;

import com.ceos18.springboot.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVo {
	private Long id;
	private String nickName;

	public static MemberVo fromEntity(Member member) {
		MemberVo memberVo = new MemberVo();
		memberVo.setId(member.getId());
		memberVo.setNickName(member.getNickName());
		return memberVo;
	}

	public Member toEntity() {
		Member member = new Member();
		member.setId(this.id);
		member.setNickName(this.nickName);
		return member;
	}
}
