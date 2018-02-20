package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.client.GeoLocationResponseDto;

import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.BoundingBox.Builder.aBoundingBox;
import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static java.util.stream.Collectors.toList;

public class GeoLocationMapper {

    public List<Location> from(List<GeoLocationResponseDto> dtos) {
        return dtos.stream().map(this::from).collect(toList());
    }

    private Location from(GeoLocationResponseDto dto) {

        List<Float> boundingBox = dto.getBoundingBox();

        return aLocation()
                .withLatitude(dto.getLatitude())
                .withLongitude(dto.getLongitude())
                .withDisplayName(dto.getDisplayName())
                .withBoundingBox(aBoundingBox()
                        .withMinLatitude(boundingBox.get(0))
                        .withMaxLatitude(boundingBox.get(1))
                        .withMinLongitude(boundingBox.get(2))
                        .withMaxLongitude(boundingBox.get(3)).build())
                .build();
        }
}
