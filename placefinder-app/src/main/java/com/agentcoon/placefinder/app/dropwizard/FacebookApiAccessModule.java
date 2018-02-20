package com.agentcoon.placefinder.app.dropwizard;

import com.agentcoon.placefinder.app.dropwizard.configuration.FacebookConfiguration;
import com.agentcoon.placefinder.app.dropwizard.configuration.PlacefinderConfiguration;
import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.infrastructure.facebook.FacebookApiGateway;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;
import io.dropwizard.setup.Environment;

import javax.inject.Singleton;

public class FacebookApiAccessModule extends AbstractModule {

    @Provides
    @Singleton
    public Facebook prepareFacebook(Environment environment, PlacefinderConfiguration configuration) {

        FacebookConfiguration facebookConfiguration = configuration.getFacebookConfiguration();

        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setRestBaseURL(facebookConfiguration.getUrl())
                .setOAuthAppId(facebookConfiguration.getAppId())
                .setOAuthAppSecret(facebookConfiguration.getAppSecret())
                .setOAuthAccessToken(facebookConfiguration.getAccessToken());

        FacebookFactory ff = new FacebookFactory(cb.build());

        return ff.getInstance();
    }

    @Override
    protected void configure() {
        bind(FacebookGateway.class).to(FacebookApiGateway.class).in(Scopes.SINGLETON);
    }
}
