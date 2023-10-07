package com.ceos18.springboot.member.presentation;

import com.ceos18.springboot.member.application.MemberService;
import com.ceos18.springboot.member.dto.request.MemberRequest;
import com.ceos18.springboot.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {

        memberService.createMember(memberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
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
