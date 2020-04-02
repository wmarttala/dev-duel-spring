package com.wmarttala.devduel.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GHRepository {

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    private boolean fork;

    @JsonProperty("forks_count")
    private String forksCount;

    @JsonProperty("stargazers_count")
    private int stars;

    @JsonProperty("open_issues")
    private int openIssues;

    private GHLanguages languages;

}
