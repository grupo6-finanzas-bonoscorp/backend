package com.example.backendBonosCorp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendBonosCorpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendBonosCorpApplication.class, args);
	}

}
