package com.ceos18.springboot.user.exception;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name() + " " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    // Security Chain에서 발생하는 에러 response 만들기
    public static void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {

        // 에러 응답코드 설정
        response.setStatus(errorCode.getHttpStatus().value());

        // 응답 body type JSON 타입으로 설정
        response.setContentType("application/json;charset=UTF-8");

        //예외 발생 시 Error 내용을 JSON화 한 후 응답 body에 담아서 보낸다
        Gson gson = new Gson();
        String responseBody = gson.toJson(new ErrorDto(errorCode.toString(), errorCode.getMessage()));

        response.getWriter().write(responseBody);
    }
}