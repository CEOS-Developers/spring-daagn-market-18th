package com.ceos18.springboot.member.application;

import com.ceos18.springboot.member.dto.request.LoginMemberRequest;
import com.ceos18.springboot.member.dto.request.SignupMemberRequest;
import com.ceos18.springboot.member.dto.response.LoginMemberResponse;
import com.ceos18.springboot.member.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {

    void createMember(SignupMemberRequest signupMemberRequest);

    LoginMemberResponse login(LoginMemberRequest loginMemberRequest);

    List<MemberResponse> getAllMembers();

    MemberResponse getMember(Long id);

    void deleteMember(Long id);
}
