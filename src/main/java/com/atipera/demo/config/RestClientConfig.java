package com.atipera.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // The correct version header for GitHub API is "X-GitHub-Api-Version: 2022-11-28"
    // since around 2020, when they've officially ended support for the API URL developer.github.com/v3
    // See: https://docs.github.com/en/rest/overview/resources-in-the-rest-api#current-version
    private static final String GITHUB_API_VERSION = "2022-11-28";
    private static final String ACCEPT_HEADER = "application/vnd.github.v3+json";

    @Value("${github.base-url}")
    private String githubBaseUrl;

    @Value("${github.token}")
    private String githubToken;

    @Bean
    public RestClient githubRestClient() {
        return RestClient.builder()
                .baseUrl(githubBaseUrl)
                .defaultHeader("X-GitHub-Api-Version", GITHUB_API_VERSION)
                .defaultHeader("Accept", ACCEPT_HEADER)
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .build();
    }
}
