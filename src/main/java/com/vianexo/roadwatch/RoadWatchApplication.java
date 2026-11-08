package com.vianexo.roadwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RoadWatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadWatchApplication.class, args);
	}

}
