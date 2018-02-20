package com.agentcoon.placefinder.mapquest.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class MapQuestConfiguration {

    @NotNull
    private final String url;

    @NotNull
    private final String appKey;

    @JsonCreator
    public MapQuestConfiguration(@JsonProperty("url") String url, @JsonProperty("appKey") String appKey) {
        this.url = url;
        this.appKey = appKey;
    }

    public String getUrl() {
        return url;
    }

    public String getAppKey() {
        return appKey;
    }
}
