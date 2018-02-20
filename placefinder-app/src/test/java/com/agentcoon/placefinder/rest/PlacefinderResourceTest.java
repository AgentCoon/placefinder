package com.agentcoon.placefinder.rest;

import com.agentcoon.placefinder.api.PlacesDto;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import com.agentcoon.placefinder.domain.placefinder.NotFoundException;
import com.agentcoon.placefinder.domain.placefinder.PlaceFinder;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PlacefinderResourceTest {

    private PlaceFinder placeFinder;
    private PlaceDtoMapper mapper;

    private PlacefinderResource placefinderResource;

    @BeforeClass
    public static void ensureServiceLocatorPopulated() {
        // workaround for https://github.com/HubSpot/dropwizard-guice/issues/95
        JerseyGuiceUtils.reset();
    }

    @Before
    public void setUp() {
        placeFinder = mock(PlaceFinder.class);
        mapper = mock(PlaceDtoMapper.class);

        placefinderResource = new PlacefinderResource(placeFinder, mapper);
    }

    @Test
    public void findLocations() throws GeoLocationException, NotFoundException {
        String country = "Finland";
        String city = "Helsinki";
        String searchString = "hygge";

        FacebookPlace place = aFacebookPlace().build();
        PlacesDto places = new PlacesDto();

        Map<String, List<FacebookPlace>> placesInLocation = new HashMap<>();

        placesInLocation.put(city, Collections.singletonList(place));

        when(placeFinder.findPlaces(country, city, searchString)).thenReturn(placesInLocation);
        when(mapper.from(placesInLocation)).thenReturn(Collections.singletonList(places));

        Response response = placefinderResource.findLocations(country, city, searchString);
        assertEquals(200, response.getStatus());

        List<PlacesDto> dtos = (List<PlacesDto>) response.getEntity();
        assertEquals(1, dtos.size());
    }

    @Test
    public void findLocationsWhenNotFound() throws GeoLocationException, NotFoundException {
        String country = "Italy";
        String city = "Rome";
        String searchString = "vineyard";

        doThrow(NotFoundException.class).when(placeFinder).findPlaces(country, city, searchString);

        Response response = placefinderResource.findLocations(country, city, searchString);
        assertEquals(404, response.getStatus());
        verify(mapper, never()).from(any());
    }

    @Test
    public void findLocationsWhenGeoLocationError() throws GeoLocationException, NotFoundException {
        String country = "Switzerland";
        String city = "Basel";
        String searchString = "pizza";

        doThrow(GeoLocationException.class).when(placeFinder).findPlaces(country, city, searchString);

        Response response = placefinderResource.findLocations(country, city, searchString);
        assertEquals(500, response.getStatus());
        verify(mapper, never()).from(any());
    }
}
