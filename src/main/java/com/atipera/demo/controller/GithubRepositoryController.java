package com.atipera.demo.controller;

import com.atipera.demo.dto.RepositoryDto;
import com.atipera.demo.service.GithubRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(GithubRepositoryController.API_BASE)
@RequiredArgsConstructor
public class GithubRepositoryController {

    public static final String API_BASE = "/api";

    private final GithubRepositoryService githubRepositoryService;

    @GetMapping("/{username}")
    public List<RepositoryDto> getRepositories(@PathVariable String username) {
        return githubRepositoryService.getRepositories(username);
    }
}
