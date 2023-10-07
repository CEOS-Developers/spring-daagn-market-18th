package com.ceos18.springboot.member.application;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.member.dto.request.MemberRequest;
import com.ceos18.springboot.member.dto.response.MemberResponse;
import com.ceos18.springboot.member.exception.MemberNotFoundException;
import com.ceos18.springboot.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(MemberRequest memberRequest) {

        Member member = memberRequest.toEntity();

        memberRepository.save(member);
    }

    public List<MemberResponse> getAllMembers() {

        List<Member> memberList = memberRepository.findAllByActivated(true);
        if (memberList.isEmpty()) {
            throw new MemberNotFoundException();
        }

        List<MemberResponse> memberResponseList = memberList.stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());

        return memberResponseList;
    }

    public MemberResponse getMember(Long id) {

        Member member = memberRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new MemberNotFoundException(id));

        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void deleteMember(Long id) {

        Member member = memberRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new MemberNotFoundException(id));

        member.updateActivatedFalse();
    }
}
