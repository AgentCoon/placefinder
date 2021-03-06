package com.agentcoon.placefinder.app.dropwizard.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class FacebookConfiguration {

    @NotNull
    private String url;

    @NotNull
    private String appId;

    @NotNull
    private String appSecret;

    @JsonCreator
    public FacebookConfiguration(@JsonProperty("url") String url, @JsonProperty("appId") String appId,
                                 @JsonProperty("appSecret") String appSecret) {
        this.url = url;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getUrl() {
        return url;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAccessToken() {
        return appId + "%7C" + appSecret;
    }
}
