package com.agentcoon.placefinder.domain.geolocation;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GeoLocationService {

    private final GeoLocationProvider geoLocationProvider;

    @Inject
    public GeoLocationService(GeoLocationProvider geoLocationProvider) {
        this.geoLocationProvider = geoLocationProvider;
    }

    public List<Location> getLocations(String country, String city) throws GeoLocationException {
        return geoLocationProvider.findLocations(country, city).stream()
                .distinct()
                .collect(toList());
    }
}
