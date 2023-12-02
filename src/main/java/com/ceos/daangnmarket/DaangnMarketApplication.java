package com.ceos.daangnmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class DaangnMarketApplication {

  public static void main(String[] args) {
    SpringApplication.run(DaangnMarketApplication.class, args);
  }

}
