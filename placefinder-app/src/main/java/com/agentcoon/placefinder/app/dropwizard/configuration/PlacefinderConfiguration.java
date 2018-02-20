package com.agentcoon.placefinder.app.dropwizard.configuration;

import com.agentcoon.placefinder.mapquest.client.MapQuestConfiguration;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class PlacefinderConfiguration extends Configuration {

    @NotNull
    private Boolean allowCORS;

    private MapQuestConfiguration mapQuestConfiguration;

    private FacebookConfiguration facebookConfiguration;

    public Boolean getAllowCORS() {
        return allowCORS;
    }

    public MapQuestConfiguration getMapQuestConfiguration() {
        return mapQuestConfiguration;
    }

    public FacebookConfiguration getFacebookConfiguration() {
        return facebookConfiguration;
    }
}
