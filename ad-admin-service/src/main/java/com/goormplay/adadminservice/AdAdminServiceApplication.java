package com.goormplay.adadminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdAdminServiceApplication.class, args);
	}

}
