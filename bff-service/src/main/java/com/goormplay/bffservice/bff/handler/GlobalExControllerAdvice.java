package com.goormplay.bffservice.bff.handler;


import com.goormplay.bffservice.bff.dto.ErrorResultDto;
import com.goormplay.bffservice.bff.dto.ResponseDto;
import com.goormplay.bffservice.bff.exception.BaseException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExControllerAdvice {

    private final KafkaTemplate<String, ErrorLogEvent> kafkaTemplate; // ErrorLogEvent를 값으로 보내도록 설정 (JsonSerializer 필요)
    private final String ERROR_LOG_TOPIC = "application-error-logs"; // 로그 전용 카프카 토픽

    // Bean Valid 검사 후 에러 처리
    @ExceptionHandler(MethodArgumentNotValidException.class) // 특정 예외 타입을 명시하는 것이 좋습니다.
    public ResponseEntity<List<ErrorResultDto>> handleValidError(MethodArgumentNotValidException e){
        List<ErrorResultDto> collect = e.getAllErrors().stream().map(o -> (FieldError) o)
                .map(o -> new ErrorResultDto(o.getField(), o.getDefaultMessage()))
                .collect(Collectors.toList());

        // 카프카로 에러 로그 전송
        ErrorLogEvent errorLog = buildErrorLogEvent(e, "Validation Error", "DTO 유효성 검사 실패", e.getMessage());
        kafkaTemplate.send(ERROR_LOG_TOPIC, errorLog.getServiceName(), errorLog); // 키는 서비스명이나 트랜잭션 ID 등

        return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
    }

    //커스텀 에러 BaseException 에러 처리
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseDto> handleBaseEx(BaseException exception){
        log.error("BaseException errorMessage(): {}",exception.getExceptionType().getErrorMessage());
        log.error("BaseException HttpStatus(): {}",exception.getExceptionType().getHttpStatus());

        // 카프카로 에러 로그 전송
        ErrorLogEvent errorLog = buildErrorLogEvent(exception, "BaseException", exception.getExceptionType().getHttpStatus().toString(), exception.getExceptionType().getErrorMessage());
        kafkaTemplate.send(ERROR_LOG_TOPIC, errorLog.getServiceName(), errorLog);

        ResponseDto responseDTO = ResponseDto.builder()
                .message(exception.getExceptionType().getErrorMessage())
                .build();
        return new ResponseEntity<>(responseDTO, exception.getExceptionType().getHttpStatus());
    }

    //IllegalArgumentException 에러 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentEx(IllegalArgumentException exception) {
        log.error("IllegalArgumentException: {}", exception.getMessage());

        // 카프카로 에러 로그 전송
        ErrorLogEvent errorLog = buildErrorLogEvent(exception, "IllegalArgumentException", "Invalid Argument", exception.getMessage());
        kafkaTemplate.send(ERROR_LOG_TOPIC, errorLog.getServiceName(), errorLog);

        ResponseDto responseDTO = ResponseDto.builder()
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    // 에러 로그 이벤트 빌더
    private ErrorLogEvent buildErrorLogEvent(Exception e, String errorType, String errorMessageSummary, String detailedMessage) {
        ErrorLogEvent errorLog = new ErrorLogEvent();
        errorLog.setServiceName("bff-service");
        errorLog.setTimestamp(java.time.Instant.now().toString());
        errorLog.setErrorType(errorType);
        errorLog.setErrorMessage(errorMessageSummary + " : " + detailedMessage);
        errorLog.setStackTrace(getStackTrace(e));
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            HttpServletRequest request = attr.getRequest();
            errorLog.setHttpMethod(request.getMethod());
            errorLog.setRequestUri(request.getRequestURI());
        }

        return errorLog;
    }
    //스택트레이스 200자 밑까지
    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String trace = sw.toString();
        return trace.length() > 200 ? trace.substring(0, 200) + "..." : trace;
    }
}
