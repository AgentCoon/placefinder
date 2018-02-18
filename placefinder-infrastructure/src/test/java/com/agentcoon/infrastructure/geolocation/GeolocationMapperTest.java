package com.agentcoon.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.BoundingBox;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.infrastructure.geolocation.GeolocationMapper;
import com.agentcoon.placefinder.mapquest.nominatim.client.NominatimResponseDto;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.agentcoon.placefinder.mapquest.nominatim.client.NominatimResponseDto.Builder.aNominatimResponse;
import static org.junit.Assert.assertEquals;

public class GeolocationMapperTest {

    private GeolocationMapper mapper = new GeolocationMapper();

    @Test
    public void fromDto() {
        Float longitude = 54.12f;
        Float latitude = 10.87f;
        Float minLongitude = 53.11f;
        Float maxLongitude = 55.45f;
        Float minLatitude = 9.25f;
        Float maxLatitude = 12.87f;
        String displayName = "Paris, France";

        NominatimResponseDto dto = aNominatimResponse()
                .withLongitude(longitude)
                .withLatitude(latitude)
                .withBoundingBox(Arrays.asList(minLongitude, maxLongitude, minLatitude, maxLatitude))
                .withDisplayName(displayName).build();

        List<Location> result = mapper.from(Arrays.asList(dto));
        Location location = result.get(0);
        assertEquals(longitude, location.getLongitude());
        assertEquals(latitude, location.getLatitude());
        assertEquals(displayName, location.getDisplayName());

        BoundingBox boundingBox = location.getBoundingBox();
        assertEquals(minLongitude, boundingBox.getMinLongitude());
        assertEquals(maxLongitude, boundingBox.getMaxLongitude());
        assertEquals(minLatitude, boundingBox.getMinLatitude());
        assertEquals(maxLatitude, boundingBox.getMaxLatitude());
    }
}
