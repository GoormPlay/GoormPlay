package com.goormplay.adadminservice.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
@Configuration
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DotenvConfig {
    /**
     * 배포 시에는 dotenv 쓰지 않고 github secret key나 aws parameter store,
     * 쿠버네티스 시크릿 사용 예정입니다
     */
    /**
     * DotenvConfig, MongoConfig
     * (중요)main-resources에 .env 추가
     * application.yml 설정
     */

    private final ConfigurableEnvironment environment;

    public DotenvConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        try {
            // resources 디렉토리의 .env 파일 로드
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(".env");
            if (inputStream == null) {
                log.error(".env file not found in classpath");
                return;
            }

            Properties props = new Properties();
            props.load(inputStream);

            Map<String, Object> envMap = props.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue().toString()
                    ));

            environment.getPropertySources()
                    .addFirst(new MapPropertySource("dotenvProperties", envMap));

            log.info("Loaded SERVICE_NAME: {}", environment.getProperty("SERVICE_NAME"));
            log.info("Loaded MONGODB_URI: {}", environment.getProperty("MONGODB_URI"));
            log.info("Loaded MONGODB_DATABASE: {}", environment.getProperty("MONGODB_DATABASE"));

        } catch (IOException e) {
            log.error("Error loading .env file", e);
        }
    }
}
