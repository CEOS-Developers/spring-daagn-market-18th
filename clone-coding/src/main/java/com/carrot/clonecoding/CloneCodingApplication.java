package com.carrot.clonecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloneCodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneCodingApplication.class, args);
	}

}
