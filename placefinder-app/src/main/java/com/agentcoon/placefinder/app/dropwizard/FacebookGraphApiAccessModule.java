package com.agentcoon.placefinder.app.dropwizard;

import com.agentcoon.placefinder.app.dropwizard.configuration.PlacefinderConfiguration;
import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.infrastructure.facebook.FacebookClientGateway;
import com.agentoon.placefinder.facebook.client.FacebookApiClient;
import com.agentoon.placefinder.facebook.client.FacebookApiGateway;
import com.agentoon.placefinder.facebook.client.FacebookClientFactory;
import com.agentoon.placefinder.facebook.client.FacebookConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;

import javax.inject.Singleton;

public class FacebookGraphApiAccessModule extends AbstractModule {

    @Provides
    @Singleton
    FacebookApiGateway prepareFacebookClient(PlacefinderConfiguration config, ObjectMapper objectMapper) {

        FacebookConfiguration facebookConfiguration = config.getFacebookConfiguration();

        FacebookApiClient facebookApiClient = new FacebookClientFactory(objectMapper)
                .create(facebookConfiguration);

        return new FacebookApiGateway(facebookApiClient, facebookConfiguration.getAccessToken());
    }

//    @Provides
//    @Singleton
//    public Facebook prepareFacebook(Environment environment, PlacefinderConfiguration configuration) throws FacebookException, FacebookClientException {
//
//        FacebookConfiguration facebookConfiguration = configuration.getFacebookConfiguration();
//
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb
//                .setOAuthAppId(facebookConfiguration.getAppId())
//                .setOAuthAppSecret(facebookConfiguration.getAppSecret())
//                .setOAuthAccessToken(facebookConfiguration.getAppId() + "|" +
//                facebookConfiguration.getAppSecret());
//
//        FacebookFactory ff = new FacebookFactory(cb.build());
//
//        Facebook f = ff.getInstance();
//        //f.extendTokenExpiration();
//
//        return f;
//
//        //return ff.getInstance();
//    }

    @Override
    protected void configure() {
        bind(FacebookGateway.class).to(FacebookClientGateway.class).in(Scopes.SINGLETON);
    }
}
