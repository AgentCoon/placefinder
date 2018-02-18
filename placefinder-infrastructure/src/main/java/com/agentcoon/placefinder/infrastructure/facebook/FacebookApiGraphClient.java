package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FacebookApiGraphClient implements FacebookGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Facebook facebook;
    private final PlaceMapper mapper;

    @Inject
    public FacebookApiGraphClient(Facebook facebook, PlaceMapper mapper) {
        this.facebook = facebook;
        this.mapper = mapper;
    }

    public List<FacebookPlace> search(Float latitude, Float longitude, Integer distance,
                                      String searchString) throws FacebookGatewayException {

        logger.info("Running a Facebook search for places near latitude: {}," +
                "longitude: {}, distance: {}, search string: {}", latitude, longitude, distance, searchString);
        GeoLocation center = new GeoLocation(latitude, longitude);

        try {
            ResponseList<Place> places = facebook.searchPlaces(searchString, center, distance);

            return places.stream()
                    .map(mapper::from)
                    .collect(toList());

        } catch (FacebookException e) {
            throw new FacebookGatewayException("An exception occurred while getting places from Facebook", e);
        }
    }
}
