package com.bforbank.tennis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.bforbank.tennis"})
@EnableJpaRepositories
public class TennisScoreComputerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisScoreComputerApplication.class, args);
	}
}
