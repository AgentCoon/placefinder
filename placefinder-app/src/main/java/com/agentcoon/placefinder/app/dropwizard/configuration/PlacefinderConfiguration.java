package com.agentcoon.placefinder.app.dropwizard.configuration;

import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimClientConfiguration;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class PlacefinderConfiguration extends Configuration {

    @NotNull
    private Boolean allowCORS;

    private NominatimClientConfiguration nominatimClientConfiguration;

    private FacebookConfiguration facebookConfiguration;

    public Boolean getAllowCORS() {
        return allowCORS;
    }

    public NominatimClientConfiguration getNominatimClientConfiguration() {
        return nominatimClientConfiguration;
    }

    public FacebookConfiguration getFacebookConfiguration() {
        return facebookConfiguration;
    }
}
