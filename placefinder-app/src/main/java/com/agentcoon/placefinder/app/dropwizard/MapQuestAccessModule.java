package com.agentcoon.placefinder.app.dropwizard;

import com.agentcoon.placefinder.app.dropwizard.configuration.PlacefinderConfiguration;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationProvider;
import com.agentcoon.placefinder.infrastructure.geolocation.GeoLocationGateway;
import com.agentcoon.placefinder.mapquest.client.GeoLocationClient;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimClient;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimClientConfiguration;
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

        NominatimClientConfiguration nominatimClientConfiguration = config.getNominatimClientConfiguration();

        NominatimClient nominatimClient = new NominatimClientFactory(objectMapper)
                .create(nominatimClientConfiguration);

        return new NominatimGateway(nominatimClient, nominatimClientConfiguration.getAppKey());
    }

    @Override
    protected void configure() {
        bind(GeoLocationProvider.class).to(GeoLocationGateway.class).in(Scopes.SINGLETON);
        bind(GeoLocationClient.class).to(NominatimGateway.class).in(Scopes.SINGLETON);
    }
}
