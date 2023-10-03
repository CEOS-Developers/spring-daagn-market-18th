package com.daagn.clonestudy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RepositoryForEmTest {

  private EntityManager em;

  @Autowired
  public RepositoryForEmTest(EntityManager em){
    this.em = em;
  }

  public void save(){
    em.persist(Member.builder().nickname("유저").town("서울시 마포구 대흥동").phoneNumber("00000000000").build());
  }

  public EntityManager getEm() {
    return em;
  }
}
