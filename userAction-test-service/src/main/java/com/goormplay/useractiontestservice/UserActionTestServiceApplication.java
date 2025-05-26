package com.goormplay.useractiontestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserActionTestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserActionTestServiceApplication.class, args);
	}

}
