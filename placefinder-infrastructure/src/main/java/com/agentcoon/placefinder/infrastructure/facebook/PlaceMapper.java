package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import com.agentoon.placefinder.facebook.client.api.FacebookLocationDto;
import com.agentoon.placefinder.facebook.client.api.FacebookPlaceDto;
import facebook4j.Place;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;

public class PlaceMapper {

    public FacebookPlace from(Place place) {
        Place.Location location = place.getLocation();

        return aFacebookPlace()
                .withLatitude(location.getLatitude())
                .withLongitude(location.getLongitude())
                .withName(place.getName()).build();
    }

    public FacebookPlace from(FacebookPlaceDto place) {
        FacebookLocationDto location = place.getLocation();

        return aFacebookPlace()
                .withLatitude(location.getLatitude())
                .withLongitude(location.getLongitude())
                .withName(place.getName()).build();
    }
}
