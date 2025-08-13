package com.atipera.demo.exception;

import java.io.Serial;

public class GithubRepositoriesNullException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6464992211513190584L;

    public GithubRepositoriesNullException(String message) {
        super(message);
    }
}
