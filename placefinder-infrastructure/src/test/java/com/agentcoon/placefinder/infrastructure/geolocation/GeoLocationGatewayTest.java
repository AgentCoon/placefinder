package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.client.exception.GeoLocationClientException;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimGateway;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimResponseDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GeoLocationGatewayTest {

    private NominatimGateway nominatimGateway;
    private GeolocationMapper geolocationMapper;

    private GeoLocationGateway geoLocationGateway;

    @Before
    public void setUp() {
        nominatimGateway = mock(NominatimGateway.class);
        geolocationMapper = mock(GeolocationMapper.class);

        geoLocationGateway = new GeoLocationGateway(nominatimGateway, geolocationMapper);
    }

    @Test
    public void findLocations() throws GeoLocationException, GeoLocationClientException {
        String country = "Spain";
        String city = "Barcelona";

        NominatimResponseDto nominatimResponse = new NominatimResponseDto.Builder().build();

        Location location = aLocation().build();
        when(nominatimGateway.findLocations(country, city)).thenReturn(Collections.singletonList(nominatimResponse));
        when(geolocationMapper.from(Collections.singletonList(nominatimResponse))).thenReturn(Collections.singletonList(location));

        List<Location> result = geoLocationGateway.findLocations(country, city);
        assertEquals(location, result.get(0));
    }

    @Test
            (expected=GeoLocationException.class)
    public void findLocationsWhenException() throws GeoLocationClientException, GeoLocationException {
        String country = "Spain";
        String city = "Barcelona";

        doThrow(GeoLocationClientException.class).when(nominatimGateway).findLocations(country, city);

        geoLocationGateway.findLocations(country, city);
    }
}
