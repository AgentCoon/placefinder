package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationProvider;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.client.GeoLocationClient;
import com.agentcoon.placefinder.mapquest.client.exception.GeoLocationClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class GeoLocationGateway implements GeoLocationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GeoLocationClient geoLocationClient;
    private final GeoLocationMapper geoLocationMapper;

    @Inject
    public GeoLocationGateway(GeoLocationClient geoLocationClient, GeoLocationMapper geoLocationMapper) {
        this.geoLocationClient = geoLocationClient;
        this.geoLocationMapper = geoLocationMapper;
    }

    @Override
    public List<Location> findLocations(String country, String city) throws GeoLocationException {

        try {
            return geoLocationMapper.from(geoLocationClient.findCities(country, city));

        } catch (GeoLocationClientException e) {
            logger.error("An exception occurred when finding locations: {}", e.getMessage());
            throw new GeoLocationException("An exception occurred when finding locations", e);
        }
    }
}
