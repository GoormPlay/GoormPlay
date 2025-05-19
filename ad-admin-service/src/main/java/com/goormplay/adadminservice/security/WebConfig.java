package com.goormplay.adadminservice.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<GatewayHeaderFilter> gatewayHeaderFilter() {
        FilterRegistrationBean<GatewayHeaderFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(new GatewayHeaderFilter());
        registration.addUrlPatterns("/*"); // 모든 URL에 적용
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE); // 가장 먼저 실행
        return registration;
    }
}