package com.daagn.clonestudy.member.domain;

import com.daagn.clonestudy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
