package com.daagn.clonestudy.common.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner implements InitializingBean {

  @PersistenceContext
  private EntityManager entityManager;

  private final List<String> tableNames = new ArrayList<>();

  @PostConstruct
  public void getTableNames(){
    List<Object[]> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();
    for (Object[] tableInfo : tableInfos) {
      String tableName = String.valueOf(tableInfo[0]);
      tableNames.add(tableName);
    }
  }

  private void truncate() {
    entityManager.createNativeQuery("SET referential_integrity" + " = 0").executeUpdate();
    for (String tableName : tableNames)
      entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
    entityManager.createNativeQuery("SET referential_integrity" + " = 1").executeUpdate();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    getTableNames();
  }

  @Transactional
  public void clear() {
    entityManager.clear();
    truncate();
  }

}
