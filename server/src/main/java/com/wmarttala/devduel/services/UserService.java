package com.wmarttala.devduel.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.wmarttala.devduel.dtos.UserDto;
import com.wmarttala.devduel.pojos.GHLanguages;
import com.wmarttala.devduel.pojos.GHRepository;
import com.wmarttala.devduel.pojos.GHUser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    RestTemplate restTemplate;

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public HttpEntity getAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer 6656cca76374a1414f86542ca1cb6dc8d09c262e");
        return new HttpEntity(headers);
    }

    public GHUser fetchUser(String username) {
        return restTemplate.exchange("https://api.github.com/users/" + username,
                HttpMethod.GET, getAuthToken(), GHUser.class).getBody();
    }

    public List<GHRepository> fetchRepositories(String username) {
        ResponseEntity<List<GHRepository>> repositoryResponse = restTemplate.exchange(
                "https://api.github.com/users/" + username + "/repos", HttpMethod.GET, getAuthToken(),
                new ParameterizedTypeReference<List<GHRepository>>() {
                });
        List<GHRepository> repositories = repositoryResponse.getBody();
        repositories.forEach(r -> r.setLanguages(fetchLanguages("https://api.github.com/repos/" + username + "/" +
                        r.getName() + "/languages")));
        return repositories;
    }

    public GHLanguages fetchLanguages(String url) {
        return new GHLanguages(restTemplate.exchange(url, HttpMethod.GET, getAuthToken(), JsonNode.class).getBody());
    }

    public UserDto getUser(String username) {
        return new UserDto(fetchUser(username), fetchRepositories(username));
    }

    public List<UserDto> getUsers(List<String> usernames) {
        return usernames
                .stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }

}
