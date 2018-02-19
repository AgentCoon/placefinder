package com.agentcoon.placefinder.domain.placefinder;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationService;
import com.agentcoon.placefinder.domain.geolocation.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.BoundingBox.Builder.aBoundingBox;
import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
    public void findPlaces() throws GeoLocationException, FacebookGatewayException {

        String country = "uk";
        String city = "york";
        String searchString = "coffee";
        Float latitude = 58.4545f;
        Float longitude = 78.4587f;

        Location location = aLocation().withLatitude(latitude)
                .withLongitude(longitude)
                .withBoundingBox(aBoundingBox()
                        .withMinLongitude(longitude)
                        .withMaxLongitude(longitude)
                        .withMinLatitude(latitude)
                        .withMaxLatitude(latitude).build())
                .build();
        FacebookPlace facebookPlace = aFacebookPlace().build();

        when(geoLocationService.getLocations(country, city)).thenReturn(Collections.singletonList(location));

        when(facebookGateway.search(eq(latitude), eq(longitude), any(), eq(searchString))).thenReturn(Collections.singletonList(facebookPlace));
        List<FacebookPlace> result = placeFinder.findPlaces(country, city, searchString);

        assertEquals(facebookPlace, result.get(0));
    }

    @Test
    public void findPlacesWhenGeolocationException() throws GeoLocationException, FacebookGatewayException {
        String country = "uk";
        String city = "york";
        String searchString = "coffee";

        doThrow(GeoLocationException.class).when(geoLocationService).getLocations(country, city);

        try {
            placeFinder.findPlaces(country, city, searchString);
            fail("Should have thrown GeoLocationException.");
        } catch (GeoLocationException e) {
            verify(facebookGateway, never()).search(any(), any(), any(), any());
        }
    }
}
