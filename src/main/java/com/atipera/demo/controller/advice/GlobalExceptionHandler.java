package com.atipera.demo.controller.advice;

import com.atipera.demo.dto.error.ErrorResponseDto;
import com.atipera.demo.exception.GithubBranchesNullException;
import com.atipera.demo.exception.GithubRepositoriesNullException;
import com.atipera.demo.exception.GithubUserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    public ErrorResponseDto handleGithubUserNotFound(GithubUserNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler({GithubRepositoriesNullException.class, GithubBranchesNullException.class})
    public ErrorResponseDto handleGithubInternal(GithubRepositoriesNullException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
