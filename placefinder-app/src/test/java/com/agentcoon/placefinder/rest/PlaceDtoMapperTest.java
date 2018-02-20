package com.agentcoon.placefinder.rest;

import com.agentcoon.placefinder.api.PlaceDto;
import com.agentcoon.placefinder.api.PlacesDto;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;
import static org.junit.Assert.assertEquals;

public class PlaceDtoMapperTest {

    private PlaceDtoMapper mapper = new PlaceDtoMapper();

    @Test
    public void from() {
        String displayName = "Display name";
        String name = "Name";
        Double latitude = 12.15d;
        Double longitude = 57.92d;

        FacebookPlace facebookPlace = aFacebookPlace()
                .withName(name)
                .withLatitude(latitude)
                .withLongitude(longitude).build();

        Map<String, List<FacebookPlace>> facebookPlaces = new HashMap<>();
        facebookPlaces.put(displayName, Collections.singletonList(facebookPlace));

        List<PlacesDto> result = mapper.from(facebookPlaces);
        assertEquals(1, result.size());

        List<PlaceDto> places = result.get(0).getPlaces();
        assertEquals(1, places.size());

        PlaceDto place = places.get(0);
        assertEquals(name, place.getName());
        assertEquals(latitude, place.getLatitude());
        assertEquals(longitude, place.getLongitude());
    }
}
