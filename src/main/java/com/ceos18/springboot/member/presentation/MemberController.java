package com.ceos18.springboot.member.presentation;

import com.ceos18.springboot.global.jwt.CustomUserDetails;
import com.ceos18.springboot.member.application.MemberService;
import com.ceos18.springboot.member.dto.request.LoginMemberRequest;
import com.ceos18.springboot.member.dto.request.SignupMemberRequest;
import com.ceos18.springboot.member.dto.response.LoginMemberResponse;
import com.ceos18.springboot.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@RequestBody SignupMemberRequest signupMemberRequest) {

        memberService.createMember(signupMemberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest) {

        return ResponseEntity.ok(memberService.login(loginMemberRequest));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<MemberResponse>> getAllMembers() {

        List<MemberResponse> memberResponseList = memberService.getAllMembers();

        return ResponseEntity.ok(memberResponseList);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {

        MemberResponse memberResponse = memberService.getMember(memberId);

        return ResponseEntity.ok(memberResponse);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.ok().build();
    }
}
