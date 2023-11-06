package com.ceos18.springboot.member.presentation;

import com.ceos18.springboot.member.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionController {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> catchMemberNotFoundException(MemberNotFoundException e) {

        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
