package com.agentoon.placefinder.facebook.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookResponseDto {

    @JsonProperty("data")
    private List<FacebookPlaceDto> facebookPlaceDtos;

    public FacebookResponseDto() {
    }

    public FacebookResponseDto(List<FacebookPlaceDto> facebookPlaceDtos) {
        this.facebookPlaceDtos = facebookPlaceDtos;
    }

    public List<FacebookPlaceDto> getFacebookPlaceDtos() {
        return facebookPlaceDtos;
    }
}
