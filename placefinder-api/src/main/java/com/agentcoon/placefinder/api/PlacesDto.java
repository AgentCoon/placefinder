package com.agentcoon.placefinder.api;

import java.util.List;

public class PlacesDto {

    private String locationDisplayName;

    private List<PlaceDto> places;

    public PlacesDto() {
    }

    public PlacesDto(String locationDisplayName, List<PlaceDto> places) {
        this.locationDisplayName = locationDisplayName;
        this.places = places;
    }

    public String getLocationDisplayName() {
        return locationDisplayName;
    }

    public List<PlaceDto> getPlaces() {
        return places;
    }
}
