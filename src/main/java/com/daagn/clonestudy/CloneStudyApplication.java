package com.daagn.clonestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloneStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneStudyApplication.class, args);
	}

}
