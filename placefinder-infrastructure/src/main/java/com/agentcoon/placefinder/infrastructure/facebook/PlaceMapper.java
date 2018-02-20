package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.Place;

import java.math.BigDecimal;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;

public class PlaceMapper {

    public FacebookPlace from(Place place) {
        Place.Location location = place.getLocation();

        return aFacebookPlace()
                .withLatitude(getAsFloat(location.getLatitude()))
                .withLongitude(getAsFloat(location.getLongitude()))
                .withName(place.getName())
                .withCity(location.getCity()).build();
    }

    private Float getAsFloat(Double value) {
        return BigDecimal.valueOf(value).floatValue();
    }
}
