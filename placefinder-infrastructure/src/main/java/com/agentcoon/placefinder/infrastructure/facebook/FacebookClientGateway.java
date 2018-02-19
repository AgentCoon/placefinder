package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import com.agentoon.placefinder.facebook.client.FacebookApiGateway;
import com.agentoon.placefinder.facebook.client.api.FacebookPlaceDto;
import com.agentoon.placefinder.facebook.client.exception.FacebookClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FacebookClientGateway implements FacebookGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FacebookApiGateway facebookApiGateway;
    private final PlaceMapper mapper;

    @Inject
    public FacebookClientGateway(FacebookApiGateway facebookApiGateway, PlaceMapper mapper) {
        this.facebookApiGateway = facebookApiGateway;
        this.mapper = mapper;
    }

    @Override
    public List<FacebookPlace> search(Float latitude, Float longitude, Integer distance, String searchString) throws FacebookGatewayException {

        logger.info("Running a Facebook search for places near latitude: {}," +
                "longitude: {}, distance: {}, search string: {}", latitude, longitude, distance, searchString);

        try {
            List<FacebookPlaceDto> places = (facebookApiGateway.searchPlaces(searchString, latitude, longitude, distance));

            return places.stream()
                    .map(mapper::from)
                    .collect(toList());

        } catch (FacebookClientException e) {
            throw new FacebookGatewayException("");
        }
    }
}
