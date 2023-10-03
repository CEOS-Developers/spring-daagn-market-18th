package com.ceos18.springboot.repository;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.member.repository.MemberRepository;
import com.ceos18.springboot.town.domain.MemberTown;
import com.ceos18.springboot.town.domain.Town;
import com.ceos18.springboot.town.repository.MemberTownRepository;
import com.ceos18.springboot.town.repository.TownRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberTownRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TownRepository townRepository;

    @Autowired
    MemberTownRepository memberTownRepository;

    @Test
    void saveMemberTown() {

        //given
        Member memberA = Member.builder()
                .password("1234")
                .nickname("AAA")
                .phone("010-1234-5678")
                .temperature(36.5)
                .email("AAA@naver.com")
                .imageUrl("A.jpg")
                .activated(true)
                .build();

        Member memberB = Member.builder()
                .password("5678")
                .nickname("BBB")
                .phone("010-2345-6789")
                .temperature(36.5)
                .email("BBB@naver.com")
                .imageUrl("B.jpg")
                .activated(true)
                .build();

        Member memberC = Member.builder()
                .password("0000")
                .nickname("CCC")
                .phone("010-9876-5432")
                .temperature(36.5)
                .email("CCC@naver.com")
                .imageUrl("C.jpg")
                .activated(true)
                .build();

        Town townA = Town.builder()
                .name("경기도 부천시 상동")
                .latitude(35.6)
                .longitude(87.4)
                .build();

        Town townB = Town.builder()
                .name("서울특별시 은평구")
                .latitude(37.4)
                .longitude(12.15)
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);

        townRepository.save(townA);
        townRepository.save(townB);

        //when
        MemberTown memberTownA = MemberTown.builder()
                .member(memberA)
                .town(townA)
                .build();

        MemberTown memberTownB = MemberTown.builder()
                .member(memberB)
                .town(townB)
                .build();

        MemberTown memberTownC = MemberTown.builder()
                .member(memberC)
                .town(townB)
                .build();

        memberTownRepository.save(memberTownA);
        memberTownRepository.save(memberTownB);
        memberTownRepository.save(memberTownC);

        //then
        assertThat(memberTownRepository.findAll().size()).isEqualTo(3);

        MemberTown savedMemberTownA = memberTownRepository.findByMemberAndTown(memberA, townA).get();
        assertThat(savedMemberTownA.getMember().getNickname()).isEqualTo("AAA");
        assertThat(savedMemberTownA.getTown().getName()).isEqualTo("경기도 부천시 상동");

        MemberTown savedMemberTownB = memberTownRepository.findByMemberAndTown(memberB, townB).get();
        assertThat(savedMemberTownB.getMember().getNickname()).isEqualTo("BBB");
        assertThat(savedMemberTownB.getTown().getName()).isEqualTo("서울특별시 은평구");

        MemberTown savedMemberTownC = memberTownRepository.findByMemberAndTown(memberC, townB).get();
        assertThat(savedMemberTownC.getMember().getNickname()).isEqualTo("CCC");
        assertThat(savedMemberTownC.getTown().getName()).isEqualTo("서울특별시 은평구");
    }
}
