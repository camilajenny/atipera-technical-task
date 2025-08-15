package com.atipera.demo.integration;

import com.atipera.demo.controller.GithubRepositoryController;
import com.atipera.demo.dto.RepositoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.EnableWireMock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@EnableWireMock
@TestPropertySource("classpath:application-test.yml")
class GithubRepositoryIntegrationTest {

    @Autowired
    private WebTestClient client;

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @Test
    void givenValidUser_whenGetRepositories_thenCorrectNonForkReposWithBranches() throws Exception {
        // given
        String username = "octocat";

        // when
        var repos = client.get()
                .uri(GithubRepositoryController.API_BASE + '/' + username)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RepositoryDto.class)
                .returnResult()
                .getResponseBody();

        // then
        assertNotNull(repos, "Response should not be null");

        assertFalse(repos.isEmpty(), "Repos list should not be empty");
        repos.forEach(repo -> {
            assertNotNull(repo.repositoryName(), "Repository name should not be null");
            assertNotNull(repo.ownerLogin(), "Owner login should not be null");

            assertNotNull(repo.branches(), "Branches should not be null");
            assertFalse(repo.branches().isEmpty(), "Branches list should not be empty");

            repo.branches().forEach(branch -> {
                assertNotNull(branch.name(), "Branch name should not be null");
                assertNotNull(branch.lastCommitSha(), "Last commit SHA should not be null");
            });
        });
    }
}
