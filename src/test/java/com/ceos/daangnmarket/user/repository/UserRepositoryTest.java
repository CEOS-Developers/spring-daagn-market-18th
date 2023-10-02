package com.ceos.daangnmarket.user.repository;

import com.ceos.daangnmarket.domain.user.entity.User;
import com.ceos.daangnmarket.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("사용자 저장")
  void saveUser() {
    // given
    User user = new User(1L, "ROLE_USER", "01012345678", true, 100.0);
    // when
    User savedUser = userRepository.save(user);
    // then
    Assertions.assertThat(user).isSameAs(savedUser);
    Assertions.assertThat(user.getPhoneNumber()).isEqualTo(savedUser.getPhoneNumber());
    Assertions.assertThat(savedUser.getId()).isNotNull();
    Assertions.assertThat(userRepository.count()).isEqualTo(1);
  }
}
