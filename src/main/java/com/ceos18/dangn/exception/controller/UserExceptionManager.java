package com.ceos18.dangn.exception.controller;

import com.ceos18.dangn.exception.custom.InvalidPasswordException;
import com.ceos18.dangn.exception.custom.UserDuplicatedException;
import com.ceos18.dangn.exception.custom.UserNotFoundException;
import com.ceos18.dangn.exception.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionManager {
    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<UserResponseDto> UserDuplicatedException(UserDuplicatedException e) {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .code(HttpStatus.CONFLICT.value())
                .httpStatus(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(userResponseDto, userResponseDto.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserResponseDto> UserNotFoundException(UserNotFoundException e) {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(userResponseDto, userResponseDto.getHttpStatus());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<UserResponseDto> InvalidPasswordException(InvalidPasswordException e) {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(userResponseDto, userResponseDto.getHttpStatus());
    }
}
