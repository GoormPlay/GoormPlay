package com.goormplay.indexingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class IndexingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndexingServiceApplication.class, args);
    }

}
