package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.Place;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aPlace;

public class PlaceMapper {

    public FacebookPlace from(Place facebookPlace) {
        return aPlace()
                .withLatitude(facebookPlace.getLocation().getLatitude())
                .withLongitude(facebookPlace.getLocation().getLongitude())
                .withDisplayName(facebookPlace.getName()).build();

    }
}
