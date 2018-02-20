package com.agentcoon.placefinder.domain.placefinder;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationService;
import com.agentcoon.placefinder.domain.geolocation.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PlaceFinderTest {

    private GeoLocationService geoLocationService;
    private FacebookGateway facebookGateway;

    private PlaceFinder placeFinder;

    @Before
    public void setUp() {
        geoLocationService = mock(GeoLocationService.class);
        facebookGateway = mock(FacebookGateway.class);

        placeFinder = new PlaceFinder(geoLocationService, facebookGateway);
    }

    @Test
    public void findPlaces() throws GeoLocationException, FacebookGatewayException, NotFoundException {
        String country = "uk";
        String city = "york";
        String searchString = "coffee";
        String placeName = "Coffee shop";
        Float latitude = 58.4545f;
        Float longitude = 78.4587f;

        Location location = aLocation().withDisplayName(city)
                .withLatitude(latitude)
                .withLongitude(longitude)
                .build();

        FacebookPlace facebookPlace = aFacebookPlace()
                .withName(placeName).build();

        when(geoLocationService.getLocations(country, city)).thenReturn(Collections.singletonList(location));
        when(facebookGateway.searchPlaces(eq(latitude), eq(longitude), any(), eq(searchString))).thenReturn(Collections.singletonList(facebookPlace));

        Map<String, List<FacebookPlace>> result = placeFinder.findPlaces(country, city, searchString);
        assertEquals(1, result.get(city).size());
        assertEquals(facebookPlace, result.get(city).get(0));
    }

    @Test
    public void findPlacesWhenNoLocationFound() throws GeoLocationException, FacebookGatewayException {
        String country = "uk";
        String city = "york";
        String searchString = "coffee";

        when(geoLocationService.getLocations(country, city)).thenReturn(Collections.EMPTY_LIST);

        try{
            placeFinder.findPlaces(country, city, searchString);
            fail("Should have thrown NotFoundException.");
        } catch (NotFoundException e) {
            verify(facebookGateway, never()).searchPlaces(any(), any(), any(), any());
        }
    }

    @Test
    public void findPlacesWhenGeolocationException() throws GeoLocationException, FacebookGatewayException, NotFoundException {
        String country = "uk";
        String city = "york";
        String searchString = "coffee";

        doThrow(GeoLocationException.class).when(geoLocationService).getLocations(country, city);

        try {
            placeFinder.findPlaces(country, city, searchString);
            fail("Should have thrown GeoLocationException.");
        } catch (GeoLocationException e) {
            verify(facebookGateway, never()).searchPlaces(any(), any(), any(), any());
        }
    }

    @Test
    public void findPlacesWhenFacebookException() throws GeoLocationException, FacebookGatewayException, NotFoundException {
        String country = "uk";
        String city = "york";
        String searchString = "coffee";
        String placeName = "Coffee shop";
        String displayName = "York, UK";
        String displayNameTwo = "York";

        Float latitude = 58.4545f;
        Float longitude = 78.4587f;
        Float latitudeTwo = 25.7895f;
        Float longitudeTwo = -21.4477f;

        Location location = aLocation().withDisplayName(displayName)
                .withLatitude(latitude)
                .withLongitude(longitude)
                .build();

        Location locationTwo = aLocation().withDisplayName(displayNameTwo)
                .withLatitude(latitudeTwo)
                .withLongitude(longitudeTwo)
                .build();


        FacebookPlace facebookPlace = aFacebookPlace().withName(placeName).build();

        when(geoLocationService.getLocations(country, city)).thenReturn(Arrays.asList(location, locationTwo));
        doThrow(FacebookGatewayException.class).when(facebookGateway).searchPlaces(eq(latitudeTwo), eq(longitudeTwo), any(), eq(searchString));
        when(facebookGateway.searchPlaces(eq(latitude), eq(longitude), any(), eq(searchString))).thenReturn(Collections.singletonList(facebookPlace));

        Map<String, List<FacebookPlace>> result = placeFinder.findPlaces(country, city, searchString);

        List<FacebookPlace> locationOneResult = result.get(displayName);
        assertEquals(1, locationOneResult.size());
        assertEquals(facebookPlace, locationOneResult.get(0));

        List<FacebookPlace> locationTwoResult = result.get(displayNameTwo);
        assertTrue(locationTwoResult.isEmpty());

        verify(facebookGateway, times(2)).searchPlaces(any(), any(), any(), any());
    }
}
