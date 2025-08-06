package com.atipera.demo.service;

import com.atipera.demo.dto.RepositoryDto;

import java.util.List;

public interface GithubRepositoryService {

    List<RepositoryDto> getRepositories(String username);
}
