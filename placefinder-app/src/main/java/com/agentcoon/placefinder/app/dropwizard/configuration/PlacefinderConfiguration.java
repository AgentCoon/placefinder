package com.agentcoon.placefinder.app.dropwizard.configuration;

import com.agentcoon.placefinder.mapquest.client.MapQuestConfiguration;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class PlacefinderConfiguration extends Configuration {

    @NotNull
    private Boolean allowCORS;

    private FacebookConfiguration facebookConfiguration;

    private MapQuestConfiguration mapQuestConfiguration;

    public Boolean getAllowCORS() {
        return allowCORS;
    }

    public FacebookConfiguration getFacebookConfiguration() {
        return facebookConfiguration;
    }

    public MapQuestConfiguration getMapQuestConfiguration() {
        return mapQuestConfiguration;
    }
}
