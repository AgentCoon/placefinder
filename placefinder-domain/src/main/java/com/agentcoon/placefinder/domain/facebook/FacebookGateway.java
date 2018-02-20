package com.agentcoon.placefinder.domain.facebook;

import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;

import java.util.List;

public interface FacebookGateway {

    List<FacebookPlace> searchPlaces(Float latitude, Float longitude, Integer distance,
                               String searchString) throws FacebookGatewayException;
}
