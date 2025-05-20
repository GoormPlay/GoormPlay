package com.goormplay.adadminservice.logger;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
@Component
public class CustomLogger {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogger.class);
    public static void logAdAdminApiRequest(
            String actionType, // e.g. CREATE_AD, GET_ADS, RECHARGE, CHECK_BALANCE
            String method,
            String advertiserId,
            HttpServletRequest request,
            Object payload  // request body or important param
    ){
        try {
            String logMessage = String.format("%s|%s|%s|%s|%s|%s",
                    actionType,
                    method,
                    advertiserId,
                    getClientIp(request),
                    request.getHeader("User-Agent"),
                    payload != null ? payload.toString() : "-"
            );
            logger.info(logMessage);
        } catch (Exception e) {
            logger.error("Error logging request: {}", e.getMessage());
        }
    }
    private static String getClientIp(HttpServletRequest request) {
        if (request == null) return "-";
        String forwarded = request.getHeader("X-Forwarded-For");
        return forwarded != null ? forwarded.split(",")[0] : request.getRemoteAddr();
    }
}
