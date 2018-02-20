package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.FacebookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class FacebookApiGateway implements FacebookGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String[] PLACE_SEARCH_FIELDS = {"location", "name"};

    private final FacebookFacade facebook;

    @Inject
    public FacebookApiGateway(FacebookFacade facebook) {
        this.facebook = facebook;
    }

    public List<FacebookPlace> searchPlaces(Float latitude, Float longitude, Integer distance, String searchString) throws FacebookGatewayException {

        logger.info("Running a Facebook search for places near latitude: {}," +
                "longitude: {}, distance: {}, search string: {}", latitude, longitude, distance, searchString);

        try {
            return facebook.searchPlaces(latitude, longitude, distance, searchString, PLACE_SEARCH_FIELDS);
        } catch (FacebookException e) {
            logger.error("An exception occurred while getting places from Facebook for latitude: {}, " +
                    "longitude: {}, distance: {}, search string: {}", latitude, longitude, distance, searchString, e);
            throw new FacebookGatewayException("An exception occurred while getting places from Facebook", e);
        }
    }
}
