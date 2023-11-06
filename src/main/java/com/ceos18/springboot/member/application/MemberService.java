package com.ceos18.springboot.member.application;

import com.ceos18.springboot.member.dto.request.MemberRequest;
import com.ceos18.springboot.member.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {

    void createMember(MemberRequest memberRequest);

    List<MemberResponse> getAllMembers();

    MemberResponse getMember(Long id);

    void deleteMember(Long id);
}
