package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimResponseDto;

import java.util.List;

import static com.agentcoon.placefinder.domain.geolocation.BoundingBox.Builder.aBoundingBox;
import static com.agentcoon.placefinder.domain.geolocation.Location.Builder.aLocation;
import static java.util.stream.Collectors.toList;

public class GeolocationMapper {

    public List<Location> from(List<NominatimResponseDto> dtos) {
        return dtos.stream().map(this::from).collect(toList());
    }

    private Location from(NominatimResponseDto dto) {

        List<Float> boundingBox = dto.getBoundingBox();

        return aLocation()
                .withLatitude(dto.getLatitude())
                .withLongitude(dto.getLongitude())
                .withDisplayName(dto.getDisplayName())
                .withBoundingBox(aBoundingBox()
                        .withMinLongitude(boundingBox.get(0))
                        .withMaxLongitude(boundingBox.get(1))
                        .withMinLatitude(boundingBox.get(2))
                        .withMaxLatitude(boundingBox.get(3)).build())
                .build();
        }
}
