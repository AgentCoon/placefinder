package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationProvider;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.client.GeoLocationClientException;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimGateway;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class GeolocationGateway implements GeoLocationProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NominatimGateway nominatimGateway;
    private final GeolocationMapper geolocationMapper;

    @Inject
    public GeolocationGateway(NominatimGateway nominatimGateway, GeolocationMapper geolocationMapper) {
        this.nominatimGateway = nominatimGateway;
        this.geolocationMapper = geolocationMapper;
    }

    @Override
    public List<Location> findLocations(String country, String city) throws GeoLocationException {

        try {
            List<NominatimResponseDto> response = nominatimGateway.findLocations(country, city);
            return geolocationMapper.from(response);

        } catch (GeoLocationClientException e) {
            logger.error("An exception occurred when finding locations: {}", e.getMessage());
            throw new GeoLocationException("An exception occurred when finding locations", e);
        }
    }
}
