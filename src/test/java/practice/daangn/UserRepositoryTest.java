package practice.daangn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import practice.daangn.domain.User;
import practice.daangn.repository.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저가 DB에 저장")
    public void saveUser() {
        // given
        User user = User.builder()
                .email("ceos@naver.com")
                .password("1234")
                .name("세오스")
                .nickname("ceos")
                .phone_number("01012345678")
                .build();
        // when
        User savedUser = userRepository.save(user);
        // then
        Assertions.assertThat(user).isSameAs(savedUser);
        Assertions.assertThat(user.getName()).isEqualTo(savedUser.getName());
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getCreated()).isNotNull();
        Assertions.assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저가 조회되는지 확인")
    public void findUser(){
        //given
        User user1 = userRepository.save(User.builder().nickname("ceos1").phone_number("01012345678").build());
        User user2 = userRepository.save(User.builder().nickname("ceos2").phone_number("01012345679").build());
        // when
        User findUser1 = userRepository.findById(user1.getId()).get();
        User findUser2 = userRepository.findById(user2.getId()).get();
        // then
        Assertions.assertThat(user1).isEqualTo(findUser1);
        Assertions.assertThat(user2).isEqualTo(findUser2);
    }

}
