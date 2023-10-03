package com.carrot.clonecoding.user;

import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Transactional
public class UserTests {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserTests.class);

    @Test
    @DisplayName("유저 저장 확인")
    public void usersave()
    {

        //given
        User user1 = new User();
        user1.setNickName("user");
        user1.setPassword("1234");
        user1.setTemperature(36.5);
        user1.setRetradingRate(80.0);
        user1.setResponseRate(80.0);
        userRepository.save(user1);
//        when
        Optional<User> retrievedUser = userRepository.findById(user1.getId());

        // then
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getNickName()).isEqualTo("user");
        assertThat(retrievedUser.get().getPassword()).isEqualTo("1234");
        assertThat(retrievedUser.get().getTemperature()).isEqualTo(36.5);
        assertThat(retrievedUser.get().getRetradingRate()).isEqualTo(80.0);
        assertThat(retrievedUser.get().getResponseRate()).isEqualTo(80.0);

        List<User> allUsers = userRepository.findAll();
        logger.info("유저목록 : ");
        for (User user : allUsers) {
            logger.info("User ID: {}, Nickname: {}", user.getId(), user.getNickName());
        }

    }


}
