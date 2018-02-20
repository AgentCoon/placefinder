package com.agentcoon.placefinder.rest;

import com.agentcoon.placefinder.api.PlaceDto;
import com.agentcoon.placefinder.api.PlacesDto;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;

import java.util.List;
import java.util.Map;

import static com.agentcoon.placefinder.api.PlaceDto.PlaceBuilder.aPlace;
import static java.util.stream.Collectors.toList;

public class PlaceDtoMapper {

    public List<PlacesDto> from(Map<String, List<FacebookPlace>> facebookPlaces){
        return facebookPlaces.entrySet().stream()
                .map(place -> new PlacesDto(place.getKey(),
                        place.getValue().stream()
                                .map(this::from)
                                .collect(toList())))
                .collect(toList());
    }

    private PlaceDto from(FacebookPlace facebookPlace) {
        return aPlace().withName(facebookPlace.getName())
                .withLongitude(facebookPlace.getLongitude())
                .withLatitude(facebookPlace.getLatitude())
                .build();
    }
}
