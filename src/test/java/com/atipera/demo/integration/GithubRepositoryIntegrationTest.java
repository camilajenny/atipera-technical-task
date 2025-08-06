package com.atipera.demo.integration;

import com.atipera.demo.controller.GithubRepositoryController;
import com.atipera.demo.dto.RepositoryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GithubRepositoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenValidUser_whenGetRepositories_thenCorrectNonForkReposWithBranches() throws Exception {
        // given
        String username = "octocat";

        // when
        String url = GithubRepositoryController.API_BASE + '/' + username;
        String response = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        assertNotNull(response, "Response should not be null");

        ObjectMapper objectMapper = new ObjectMapper();
        List<RepositoryDto> repos = objectMapper.readValue(response, new TypeReference<>() {
        });

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
