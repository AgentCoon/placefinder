package com.agentcoon.placefinder.domain.geolocation;

import java.util.List;

public interface GeoLocationProvider {

    List<Location> findLocations(String country, String city) throws GeoLocationException;
}
