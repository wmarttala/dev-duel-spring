package com.wmarttala.devduel.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GHUser {

    private String login;

    private String name;

    private String location;

    private String bio;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private int followers;

    private int following;

}
