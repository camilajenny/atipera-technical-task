package com.atipera.demo.exception;

import java.io.Serial;

public class GithubBranchesNullException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2829666614230056588L;

    public GithubBranchesNullException(String message) {
        super(message);
    }
}
