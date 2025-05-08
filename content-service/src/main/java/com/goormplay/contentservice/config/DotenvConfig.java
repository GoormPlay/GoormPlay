package com.goormplay.contentservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // MongoConfig보다 먼저 실행 설정
public class DotenvConfig {
    /**
     * 배포 시에는 dotenv 쓰지 않고 github secret key나 aws parameter store,
     * 쿠버네티스 시크릿 사용 예정입니다
     */

    private final ConfigurableEnvironment environment;

    public DotenvConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        String projectRoot = new File("").getAbsolutePath();

        Dotenv dotenv = Dotenv.configure()
                .directory(projectRoot)
                .ignoreIfMissing() // .env 없어도 오류 X
                .load();

        Map<String, Object> envMap = dotenv.entries().stream()
                .collect(Collectors.toMap(
                        DotenvEntry::getKey,
                        DotenvEntry::getValue
                ));
        environment.getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", envMap));
        log.info("Loaded Environment properties, MongoDB URI: {}", environment.getProperty("MONGODB_URI"));
    }
}
