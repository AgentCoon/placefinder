package com.agentcoon.placefinder.app.dropwizard.configuration;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class PlacefinderConfiguration extends Configuration {

    @NotNull
    private Boolean allowCORS;

    public Boolean getAllowCORS() {
        return allowCORS;
    }
}
