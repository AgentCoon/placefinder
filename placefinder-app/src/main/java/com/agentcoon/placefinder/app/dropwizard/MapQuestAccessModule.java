package com.agentcoon.placefinder.app.dropwizard;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationProvider;
import com.agentcoon.placefinder.infrastructure.geolocation.GeolocationGateway;
import com.agentcoon.placefinder.mapquest.client.MapQuestClientFactory;
import com.agentcoon.placefinder.mapquest.client.MapQuestConfiguration;
import com.agentcoon.placefinder.app.dropwizard.configuration.PlacefinderConfiguration;
import com.agentcoon.placefinder.mapquest.client.MapQuestClient;
import com.agentcoon.placefinder.mapquest.client.MapQuestGateway;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimClient;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimClientFactory;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

public class MapQuestAccessModule extends AbstractModule {

    @Provides
    @Singleton
    MapQuestGateway mapQuestGateway(PlacefinderConfiguration config, ObjectMapper objectMapper) {

        MapQuestConfiguration mapQuestConfiguration = config.getMapQuestConfiguration();

        MapQuestClient mapQuestClient = new MapQuestClientFactory(objectMapper)
                .create(mapQuestConfiguration);

        return new MapQuestGateway(mapQuestClient, mapQuestConfiguration.getAppKey());
    }

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
