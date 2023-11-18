package com.ceos18.springboot.global.exception;

import com.ceos18.springboot.member.exception.MemberException;
import com.ceos18.springboot.post.exception.PostException;
import com.ceos18.springboot.town.exception.TownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<String> catchMemberException(MemberException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<String> catchPostException(PostException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }

    @ExceptionHandler(TownException.class)
    public ResponseEntity<String> catchTownException(TownException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }
}
