package com.agentcoon.placefinder.app.dropwizard.configuration;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class FacebookConfiguration extends Configuration {

    @NotNull
    private String appId;

    @NotNull
    private String appSecret;

    @NotNull
    private String accessToken;

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
