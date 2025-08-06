package com.atipera.demo.integration.github.dto;

public record BranchApiResponse(String name,
                                CommitApiResponse commit) {
    public record CommitApiResponse(String sha) {
    }
}
