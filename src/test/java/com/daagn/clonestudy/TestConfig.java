package com.daagn.clonestudy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

  @PersistenceContext
  private EntityManager em;

  @Bean
  public RepositoryForEmTest repositoryForEmTest(){
    return new RepositoryForEmTest(em);
  }

}
