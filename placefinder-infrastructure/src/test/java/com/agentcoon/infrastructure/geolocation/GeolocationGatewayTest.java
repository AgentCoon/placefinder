package com.agentcoon.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.infrastructure.geolocation.GeolocationGateway;
import com.agentcoon.placefinder.infrastructure.geolocation.GeolocationMapper;
import com.agentcoon.placefinder.mapquest.client.GeoLocationClientException;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimGateway;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimResponseDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GeolocationGatewayTest {

    private NominatimGateway nominatimGateway;
    private GeolocationMapper geolocationMapper;

    private GeolocationGateway geolocationGateway;

    @Before
    public void setUp() {
        nominatimGateway = mock(NominatimGateway.class);
        geolocationMapper = mock(GeolocationMapper.class);

        geolocationGateway = new GeolocationGateway(nominatimGateway, geolocationMapper);
    }

    @Test
    public void findLocations() throws GeoLocationException, GeoLocationClientException {
        String country = "Spain";
        String city = "Barcelona";

        NominatimResponseDto nominatimResponse = new NominatimResponseDto.Builder().build();

        Location location = aLocation().build();
        when(nominatimGateway.findLocations(country, city)).thenReturn(Collections.singletonList(nominatimResponse));
        when(geolocationMapper.from(Collections.singletonList(nominatimResponse))).thenReturn(Collections.singletonList(location));

        List<Location> result = geolocationGateway.findLocations(country, city);
        assertEquals(location, result.get(0));
    }

    @Test
            (expected=GeoLocationException.class)
    public void findLocationsWhenException() throws GeoLocationClientException, GeoLocationException {
        String country = "Spain";
        String city = "Barcelona";

        doThrow(GeoLocationClientException.class).when(nominatimGateway).findLocations(country, city);

        geolocationGateway.findLocations(country, city);
    }
}
