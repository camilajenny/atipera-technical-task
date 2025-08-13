package com.atipera.demo.controller.advice;

import com.atipera.demo.dto.error.ErrorResponseDto;
import com.atipera.demo.exception.GithubUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    public ErrorResponseDto handleGithubUserNotFound(GithubUserNotFoundException ex) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
