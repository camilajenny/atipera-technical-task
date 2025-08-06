## GitHub Repository API

Spring Boot 3.5.4 + Java 21 application that lists non-fork GitHub repositories for a given user,
including branch name and last commit SHA.

## Features

- Lists non-fork repositories
- For each repository: name, owner login, branches with last commit SHA
- Returns 404 JSON for non-existing users

## GitHub API Token

This app requires a **GitHub Personal Access Token** to authenticate API requests.

1. Create a token in
   your [GitHub settings → Developer settings → Personal access tokens](https://github.com/settings/tokens).
2. **Required scopes**: `repo` (read-only for public repos is fine).
3. Make sure to set your `GITHUB_TOKEN` environment variable before running the app.
   You can do this in IntelliJ via Run → Edit Configurations → Environment variables — just add
   ```
   GITHUB_TOKEN=your_token_here
   ```
   there.

## Run

```bash
./gradlew bootRun
```

## Example Request

```bash
GET http://localhost:8080/api/octocat
```

Example Response

```json
[
  {
    "repositoryName": "Spoon-Knife",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "d0dd1f61b33d64e29d8bc1372a94ef6a2fee76a9"
      }
    ]
  }
]
```

Example 404 Response

```json
{
  "status": 404,
  "message": "GitHub user 'nonexistentuser' not found"
}
```
