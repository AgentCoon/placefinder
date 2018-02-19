package com.agentoon.placefinder.facebook.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookPlaceDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private FacebookLocationDto location;

    public FacebookPlaceDto() {
    }

    public FacebookPlaceDto(String name, FacebookLocationDto location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public FacebookLocationDto getLocation() {
        return location;
    }
}
