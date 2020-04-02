package com.wmarttala.devduel.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wmarttala.devduel.pojos.GHRepository;
import com.wmarttala.devduel.pojos.GHUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String username;

    private String name;

    private String location;

    private String bio;

    @JsonProperty("avatar-url")
    private String avatarUrl;

    private List<String> titles;

    @JsonProperty("favorite-language")
    private String favoriteLanguage;

    @JsonProperty("public-repos")
    private int publicRepos;

    @JsonProperty("total-stars")
    private int totalStars;

    @JsonProperty("highest-starred")
    private int highestStarred;

    @JsonProperty("perfect-repos")
    private int perfectRepos;

    private int followers;

    private int following;

    public UserDto(GHUser ghUser, List<GHRepository> repositories) {
        setUsername(ghUser.getLogin());
        setName(ghUser.getName());
        setLocation(ghUser.getLocation());
        setBio(ghUser.getBio());
        setAvatarUrl(ghUser.getAvatarUrl());
        setFollowers(ghUser.getFollowers());
        setFollowing(ghUser.getFollowing());
        setTitles(repositories);
        setFavoriteLanguage(repositories);
        setPublicRepos(repositories.size());
        setTotalStars(repositories);
        setHighestStarred(repositories);
        setPerfectRepos(repositories);
    }

    public void setTitles(List<GHRepository> repositories) {
        titles = new ArrayList<>();

        int forkCount = repositories
                .stream()
                .reduce(0, (acc, next) -> next.isFork() ? acc + 1 : acc, Integer::sum);

        Set<String> languages = repositories
                .stream()
                .map(r -> r.getLanguages().getNames())
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(HashSet::new));

        if ((repositories.size() / 2) <= forkCount) {
            titles.add("Forker");
        }
        if (languages.size() == 1) {
            titles.add("One-Trick Pony");
        }
        if (languages.size() > 10) {
            titles.add("Jack of all Trades");
        }
        if ((followers * 2) <= following) {
            titles.add("Stalker");
        }
        if ((following * 2) <= followers) {
            titles.add("Mr. Popular");
        }
    }

    public void setFavoriteLanguage(List<GHRepository> repositories) {
        Map<String, Integer> languageCounts = new HashMap<>();
        for (GHRepository repository : repositories) {
            for (String language : repository.getLanguages().getNames()) {
                if (language != null) {
                    if (languageCounts.containsKey(language)) {
                        languageCounts.put(language, languageCounts.get(language) + 1);
                    } else {
                        languageCounts.put(language, 1);
                    }
                }
            }
        }
        int favoriteCount = 0;
        for (String language : languageCounts.keySet()) {
            int languageCount = languageCounts.get(language);
            if (languageCount > favoriteCount) {
                favoriteLanguage = language;
                favoriteCount = languageCount;
            }
        }
    }

    public void setTotalStars(List<GHRepository> repositories) {
        totalStars = repositories
                .stream()
                .map(GHRepository::getStars)
                .reduce(0, Integer::sum);
    }

    public void setHighestStarred(List<GHRepository> repositories) {
        highestStarred = repositories
                .stream()
                .map(GHRepository::getStars)
                .reduce(0, (max, next) -> next > max ? next : max);
    }

    public void setPerfectRepos(List<GHRepository> repositories) {
        perfectRepos = repositories
                .stream()
                .map(GHRepository::getOpenIssues)
                .reduce(0, (acc, next) -> next == 0 ? acc + 1 : acc);
    }

}
