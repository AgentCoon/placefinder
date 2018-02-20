package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.client.GeoLocationClient;
import com.agentcoon.placefinder.mapquest.client.GeoLocationResponseDto;
import com.agentcoon.placefinder.mapquest.client.exception.GeoLocationClientException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GeoLocationGatewayTest {

    private GeoLocationClient geoLocationClient;
    private GeoLocationMapper geoLocationMapper;

    private GeoLocationGateway geoLocationGateway;

    @Before
    public void setUp() {
        geoLocationClient = mock(GeoLocationClient.class);
        geoLocationMapper = mock(GeoLocationMapper.class);

        geoLocationGateway = new GeoLocationGateway(geoLocationClient, geoLocationMapper);
    }

    @Test
    public void findLocations() throws GeoLocationException, GeoLocationClientException {
        String country = "Spain";
        String city = "Barcelona";

        GeoLocationResponseDto nominatimResponse = new GeoLocationResponseDto.Builder().build();

        Location location = aLocation().build();
        when(geoLocationClient.findCities(country, city)).thenReturn(Collections.singletonList(nominatimResponse));
        when(geoLocationMapper.from(Collections.singletonList(nominatimResponse))).thenReturn(Collections.singletonList(location));

        List<Location> result = geoLocationGateway.findLocations(country, city);
        assertEquals(location, result.get(0));
    }

    @Test
            (expected=GeoLocationException.class)
    public void findLocationsWhenException() throws GeoLocationClientException, GeoLocationException {
        String country = "Spain";
        String city = "Barcelona";

        doThrow(GeoLocationClientException.class).when(geoLocationClient).findCities(country, city);

        geoLocationGateway.findLocations(country, city);
    }
}
