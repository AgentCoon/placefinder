package com.agentcoon.placefinder.infrastructure.geolocation;

import com.agentcoon.placefinder.domain.geolocation.BoundingBox;
import com.agentcoon.placefinder.domain.geolocation.Location;
import com.agentcoon.placefinder.mapquest.client.nominatim.NominatimResponseDto;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.agentcoon.placefinder.mapquest.client.nominatim.NominatimResponseDto.Builder.aNominatimResponse;
import static org.junit.Assert.assertEquals;

public class GeolocationMapperTest {

    private GeolocationMapper mapper = new GeolocationMapper();

    @Test
    public void fromDto() {
        Float longitude = 16.9335199f;
        Float latitude = 52.4082663f;
        Float minLongitude = 16.7315878f;
        Float maxLongitude = 17.0717011f;
        Float minLatitude = 52.2919238f;
        Float maxLatitude = 52.5093282f;
        String displayName = "Paris, France";

        NominatimResponseDto dto = aNominatimResponse()
                .withLongitude(longitude)
                .withLatitude(latitude)
                .withBoundingBox(Arrays.asList(minLatitude, maxLatitude, minLongitude, maxLongitude))
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
