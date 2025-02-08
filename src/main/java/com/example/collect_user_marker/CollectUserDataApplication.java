package com.example.collect_user_marker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CollectUserDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectUserDataApplication.class, args);
	}

}
