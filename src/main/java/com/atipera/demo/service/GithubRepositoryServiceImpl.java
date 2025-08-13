package com.atipera.demo.service;

import com.atipera.demo.dto.BranchDto;
import com.atipera.demo.dto.RepositoryDto;
import com.atipera.demo.exception.GithubBranchesNullException;
import com.atipera.demo.exception.GithubRepositoriesNullException;
import com.atipera.demo.exception.GithubUserNotFoundException;
import com.atipera.demo.integration.github.dto.BranchApiResponse;
import com.atipera.demo.integration.github.dto.RepositoryApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class GithubRepositoryServiceImpl implements GithubRepositoryService {

    private final RestClient restClient;

    @Value("${github.repos-path}")
    private String GITHUB_API_REPOS_PATH;

    @Value("${github.branches-path}")
    private String GITHUB_API_BRANCHES_PATH;

    @Override
    public List<RepositoryDto> getRepositories(String username) {
        try {
            var repos = restClient.get()
                    .uri(GITHUB_API_REPOS_PATH, username)
                    .retrieve()
                    .body(RepositoryApiResponse[].class);

            if (repos == null) {
                throw new GithubRepositoriesNullException("Got repos null from the API");
            }

            return Arrays.stream(repos)
                    .filter(repo -> !repo.fork())
                    .map(repo -> {
                        BranchApiResponse[] branches = restClient.get()
                                .uri(GITHUB_API_BRANCHES_PATH,
                                        repo.owner().login(), repo.name())
                                .retrieve()
                                .body(BranchApiResponse[].class);

                        if (branches == null) {
                            throw new GithubBranchesNullException("Got branches null from the API");
                        }

                        var branchDtos = Arrays.stream(branches)
                                        .map(b -> new BranchDto(b.name(), b.commit().sha()))
                                        .toList();

                        return new RepositoryDto(repo.name(), repo.owner().login(), branchDtos);
                    })
                    .toList();

        } catch (HttpClientErrorException.NotFound e) {
            throw new GithubUserNotFoundException("GitHub user '" + username + "' not found");
        }
    }
}


