package com.agentcoon.placefinder.mapquest.client.nominatim;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class NominatimClientConfiguration {

    @NotNull
    private final String url;

    @NotNull
    private final String appKey;

    @JsonCreator
    public NominatimClientConfiguration(@JsonProperty("url") String url, @JsonProperty("appKey") String appKey) {
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
