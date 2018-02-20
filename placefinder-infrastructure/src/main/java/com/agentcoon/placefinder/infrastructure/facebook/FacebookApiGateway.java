package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.FacebookException;
import facebook4j.Place;
import facebook4j.ResponseList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FacebookApiGateway implements FacebookGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String[] PLACE_SEARCH_FIELDS = {"location", "name"};

    private final FacebookFacade facebook;
    private final PlaceMapper mapper;

    @Inject
    public FacebookApiGateway(FacebookFacade facebook, PlaceMapper mapper) {
        this.facebook = facebook;
        this.mapper = mapper;
    }

    public List<FacebookPlace> searchPlaces(Float latitude, Float longitude, Integer distance, String searchString) throws FacebookGatewayException {

        logger.info("Running a Facebook search for places near latitude: {}," +
                "longitude: {}, distance: {}, search string: {}", latitude, longitude, distance, searchString);

        try {
            ResponseList<Place> places = facebook.searchPlaces(latitude, longitude, distance, searchString, PLACE_SEARCH_FIELDS);

            return places.stream().map(mapper::from).collect(toList());

        } catch (FacebookException e) {
            logger.error("An exception occurred while getting places from Facebook for latitude: {}, " +
                    "longitude: {}, distance: {}, search string: {}", latitude, longitude, distance, searchString, e);
            throw new FacebookGatewayException("An exception occurred while getting places from Facebook", e);
        }
    }
}
