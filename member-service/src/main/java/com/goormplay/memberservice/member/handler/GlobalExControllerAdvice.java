package com.goormplay.memberservice.member.handler;

import com.goormplay.memberservice.member.dto.ErrorResultDto;
import com.goormplay.memberservice.member.dto.ResponseDto;
import com.goormplay.memberservice.member.exception.BaseException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExControllerAdvice {


    // Bean Valid 검사 후 에러 처리
    @ExceptionHandler
    public Object memberValidError(MethodArgumentNotValidException e){
        List<ErrorResultDto> collect = e.getAllErrors().stream().map(o -> (FieldError) o)
                .map(o -> new ErrorResultDto(o.getField(), o.getDefaultMessage()))
                .collect(Collectors.toList());
        return collect;
    }

    //BaseException 에러 처리
    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleBaseEx(BaseException exception){
        log.error("BaseException errorMessage(): {}",exception.getExceptionType().getErrorMessage());
        log.error("BaseException HttpStatus(): {}",exception.getExceptionType().getHttpStatus());
        ResponseDto responseDTO = ResponseDto.builder()
                .message(exception.getExceptionType().getErrorMessage())
                .build();
        return new ResponseEntity<>(responseDTO, exception.getExceptionType().getHttpStatus());
    }

    //IllegalArgumentException 에러 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentEx(IllegalArgumentException exception) {
        ResponseDto responseDTO = ResponseDto.builder()
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
    
    //FeignException 에러 처리

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ResponseDto> handleFeignExceptionEx(FeignException exception) {
        ResponseDto responseDTO = ResponseDto.builder()
                .message(exception.getMessage()+"feign 오류")
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
