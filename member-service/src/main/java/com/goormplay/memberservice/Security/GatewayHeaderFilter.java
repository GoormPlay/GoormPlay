package com.goormplay.memberservice.Security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

public class GatewayHeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String fromGateway = request.getHeader("X-From-Gateway");
        if (!"true".equals(fromGateway)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Request");
        }
        chain.doFilter(req, res); // 다음 필터로 요청 전달
    }
}