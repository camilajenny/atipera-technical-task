package com.atipera.demo.exception;

import java.io.Serial;

public class GithubUserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5942122452224599830L;

    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
