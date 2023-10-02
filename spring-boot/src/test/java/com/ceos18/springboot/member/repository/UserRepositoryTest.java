package com.ceos18.springboot.member.repository;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.town.domain.Town;
import com.ceos18.springboot.town.repository.TownRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private TownRepository townRepository;

    @Test
    @DisplayName("유저 findById 작동 테스트")
    public void findbyUserid() {

        //given
        Town townA = Town.builder()
                .name("경기도 수원시 영통구")
                .x(33.8)
                .y(12.3)
                .build();

        Town townB = Town.builder()
                .name("서울시 서대문구 아현동")
                .x(100.2)
                .y(15.0)
                .build();

        Town townC = Town.builder()
                .name("경기도 구리시 교문동")
                .x(130.0)
                .y(4.0)
                .build();

        townRepository.save(townA);
        townRepository.save(townB);
        townRepository.save(townC);

        User userA = User.builder()
                .imgUrl("a.jpg")
                .name("김수한")
                .nick("수수")
                .pwd("1234")
                .phone("01022223333")
                .town(townA)
                .build();

        User userB = User.builder()
                .imgUrl("b.jpg")
                .name("철수한")
                .nick("척척")
                .pwd("2222")
                .phone("01033334444")
                .town(townB)
                .build();

        User userC = User.builder()
                .imgUrl("c.jpg")
                .name("한석봉")
                .nick("떡떡")
                .pwd("3333")
                .phone("01055554444")
                .town(townC)
                .build();


        User savedUserA = userRepository.save(userA);
        User savedUserB = userRepository.save(userB);
        User savedUserC = userRepository.save(userC);

        // then
        User findUserA = userRepository.findById(userA.getId()).get();
        User findUserB = userRepository.findById(userB.getId()).get();
        User findUserC = userRepository.findById(userC.getId()).get();

        //when
        Assertions.assertThat(savedUserA).isSameAs(findUserA);
        Assertions.assertThat(savedUserB).isSameAs(findUserB);
        Assertions.assertThat(savedUserC).isSameAs(findUserC);

    }
}