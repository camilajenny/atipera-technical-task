package com.atipera.demo.integration;

import com.atipera.demo.controller.GithubRepositoryController;
import com.atipera.demo.dto.RepositoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@EnableWireMock
@ConfigureWireMock(filesUnderClasspath = "wiremock")
class GithubRepositoryIntegrationTest {

    @Autowired
    private WebTestClient client;

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

        var mapper = new ObjectMapper();
        var actualJson = mapper.writeValueAsString(repos);

        // then
        var expectedJson = """
                [
                    {
                        "repositoryName": "git-consortium",
                        "ownerLogin": "octocat",
                        "branches": [
                            {
                              "name": "bower",
                              "lastCommitSha": "f041b09c21e5b0e8e2308fce6c595c4df42f2633"
                            }
                        ]
                    }
                ]
                """;
        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }
}


