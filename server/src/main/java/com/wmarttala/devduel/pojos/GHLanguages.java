package com.wmarttala.devduel.pojos;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GHLanguages {

    private List<String> names;

    public GHLanguages(JsonNode languages) {
        this.names = new ArrayList<>();
        if (languages != null) {
            languages.fieldNames().forEachRemaining(names::add);
        }
    }

}
