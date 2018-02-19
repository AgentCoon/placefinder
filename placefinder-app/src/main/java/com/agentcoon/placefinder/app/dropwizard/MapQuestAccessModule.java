package com.agentcoon.placefinder.app.dropwizard;

import com.agentcoon.placefinder.app.dropwizard.configuration.PlacefinderConfiguration;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationProvider;
import com.agentcoon.placefinder.infrastructure.geolocation.GeolocationGateway;
import com.agentcoon.placefinder.mapquest.client.MapQuestConfiguration;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimClient;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimClientFactory;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

public class MapQuestAccessModule extends AbstractModule {

    @Provides
    @Singleton
    NominatimGateway nominatimGateway(PlacefinderConfiguration config, ObjectMapper objectMapper) {

        MapQuestConfiguration mapQuestConfiguration = config.getMapQuestConfiguration();

        NominatimClient nominatimClient = new NominatimClientFactory(objectMapper)
                .create(mapQuestConfiguration);

        return new NominatimGateway(nominatimClient, mapQuestConfiguration.getAppKey());
    }

    @Override
    protected void configure() {

        bind(GeoLocationProvider.class).to(GeolocationGateway.class).in(Scopes.SINGLETON);
    }
}
