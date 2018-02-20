package com.agentcoon.placefinder.app.dropwizard;

import com.agentcoon.placefinder.app.dropwizard.autoinject.ApplicationHealthCheck;
import com.agentcoon.placefinder.app.dropwizard.configuration.PlacefinderConfiguration;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class PlacefinderApplication extends Application<PlacefinderConfiguration> {

    private GuiceBundle<PlacefinderConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new PlacefinderApplication().run(args);
    }

    @Override
    public String getName() {
        return "placefinder";
    }

    @Override
    public void initialize(Bootstrap<PlacefinderConfiguration> bootstrap) {

        guiceBundle = GuiceBundle.<PlacefinderConfiguration>newBuilder()
                .addModule(new FacebookApiAccessModule())
                .addModule(new MapQuestAccessModule())
                .enableAutoConfig("com.agentcoon.placefinder.rest",
                        "com.agentcoon.placefinder.app.dropwizard.autoinject")
                .setConfigClass(PlacefinderConfiguration.class)
                .build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(PlacefinderConfiguration config, Environment env) {

        if (config.getAllowCORS()) {
            final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

            cors.setInitParameter("allowedOrigins", "*");
            cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
            cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

            cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        }

        env.healthChecks().register("Placefinder Application HealthCheck", new ApplicationHealthCheck());
    }
}
