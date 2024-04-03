package com.odangsa.hashtag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HashtagApplication {

	public static void main(String[] args) {
		SpringApplication.run(HashtagApplication.class, args);
	}

}
