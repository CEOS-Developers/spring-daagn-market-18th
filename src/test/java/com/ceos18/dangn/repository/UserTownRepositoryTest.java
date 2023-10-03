package com.ceos18.dangn.repository;

import com.ceos18.dangn.domain.Town;
import com.ceos18.dangn.domain.User;
import com.ceos18.dangn.domain.UserTown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTownRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired TownRepository townRepository;
    @Autowired UserTownRepository userTownRepository;

    @Test
    @DisplayName("UserTownRepository 테스트")
    public void save() {
        //given
        User user1 = User.builder()
                .phone("01041457327")
                .nickname("쿠키")
                .build();
        userRepository.save(user1);
        User user2 = User.builder()
                .phone("01057143220")
                .nickname("당근당근")
                .build();
        userRepository.save(user2);

        Town town1 = Town.builder()
                .townName("방배동")
                .districtName("서초구")
                .stateName("서울시")
                .build();
        townRepository.save(town1);
        Town town2 = Town.builder()
                .townName("상수동")
                .districtName("마포구")
                .stateName("서울시")
                .build();
        townRepository.save(town2);

        //when
        UserTown userTown1 = UserTown.builder()
                .user(user1)
                .town(town1)
                .build();
        userTownRepository.save(userTown1);
        UserTown userTown2 = UserTown.builder()
                .user(user1)
                .town(town2)
                .build();
        userTownRepository.save(userTown2);

        UserTown userTown3 = UserTown.builder()
                .user(user2)
                .town(town1)
                .build();
        userTownRepository.save(userTown3);


        //then
        assertThat(userTownRepository.findAll().size()).isEqualTo(3);
        assertThat(userTownRepository.findByUser(user1).size()).isEqualTo(2);
        assertThat(userTownRepository.findByUserAndTown(user1, town1).size()).isEqualTo(1);

        assertThat(userTownRepository.findByUser(user2).get(0).getTown().getTownName()).isEqualTo("방배동");

        UserTown findUserTown = userTownRepository.findByTown(town2).get();
        assertThat(findUserTown.getUser().getPhone()).isEqualTo("01041457327");
    }
}