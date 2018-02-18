package com.agentcoon.placefinder.mapquest.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapQuestConfiguration {

    private final String url;
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
