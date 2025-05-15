package com.goormplay.adadminservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class FileUploadConfig {
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        }catch (IOException e){
            throw new RuntimeException("could not create directory");
        }
    }
}
