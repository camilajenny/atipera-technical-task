package com.atipera.demo.integration.github.dto;

public record RepositoryApiResponse(String name,
                                    Owner owner,
                                    boolean fork) {
    public record Owner(String login) {
    }
}
