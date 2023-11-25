package com.daagn.clonestudy.common.service;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public abstract class ServiceTest {

  @Autowired
  private DatabaseCleaner databaseCleaner;
  @Autowired
  private MemberRepository memberRepository;

  @BeforeEach
  void beforeEach(){
    databaseCleaner.clear();
  }

  public Member 유저_등록하기(String nickname, String town, String phoneNumber){
    return memberRepository.save(Member.builder()
        .nickname(nickname)
        .password("test password")
        .town(town)
        .phoneNumber(phoneNumber)
        .build());
  }

}
