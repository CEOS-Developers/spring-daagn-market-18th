package com.daagn.clonestudy;

import io.micrometer.observation.GlobalObservationConvention;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
public class JpaRepositoryTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private PostRepository postRepository;
  
  @Autowired
  private RepositoryForEmTest repository;

  @Test
  @DisplayName("빈에 등록된 JPA 레포지토리 클래스 정보를 확인한다.")
  void JPA_레포지토리_클래스_정보_확인하기(){
    logger.info("Class Info: {}", postRepository.getClass());
  }
  
  @Test
  @DisplayName("스프링 레포지토리에 Entity Manager가 주입되는 방식을 확인한다.")
  void EntityManager_주입되는방식_확인하기() {
    repository.save();
    logger.info("Entity Manager Info: {}", repository.getEm().getClass());
  }
}
