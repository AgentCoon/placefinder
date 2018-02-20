package com.agentcoon.placefinder.mapquest.client;

import com.agentcoon.placefinder.mapquest.client.exception.GeoLocationClientException;

import java.util.List;

public interface GeoLocationClient {

    List<GeoLocationResponseDto> findCities(String country, String city) throws GeoLocationClientException;
}
