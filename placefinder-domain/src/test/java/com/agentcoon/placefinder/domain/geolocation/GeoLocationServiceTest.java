package com.agentcoon.placefinder.domain.geolocation;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GeoLocationServiceTest {

    private GeoLocationProvider geoLocationProvider;

    private GeoLocationService geoLocationService;

    @Before
    public void setUp() {
        geoLocationProvider = mock(GeoLocationProvider.class);

        geoLocationService = new GeoLocationService(geoLocationProvider);
    }

    @Test
    public void getLocations() throws GeoLocationException {
        String country = "poland";
        String city = "poznan";
        Location location = aLocation().build();

        when(geoLocationProvider.findLocations(country, city)).thenReturn(Collections.singletonList(location));

        List<Location> result = geoLocationService.getLocations(country, city);
        assertEquals(location, result.get(0));
        verify(geoLocationProvider, times(1)).findLocations(country, city);
    }

    @Test
            (expected=GeoLocationException.class)
    public void getLocationsWhenException() throws GeoLocationException {
        String country = "germany";
        String city = "berlin";

        doThrow(GeoLocationException.class).when(geoLocationProvider).findLocations(country, city);

        geoLocationService.getLocations(country, city);
    }
}
