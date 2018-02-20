package com.agentcoon.placefinder.domain.geolocation;

import org.junit.Test;

import static com.agentcoon.placefinder.domain.geolocation.BoundingBox.Builder.aBoundingBox;
import static org.junit.Assert.assertEquals;

public class LocationTest {

    @Test
    public void calculateDistanceTest() {

        Float latitude = 52.4082663f;
        Float longitude = 16.9335199f;

        Float maxLongitude = 17.0717011f;
        Float maxLatitude = 52.5093282f;
        Float minLongitude = 16.7315878f;
        Float minLatitude = 52.2919238f;

        Integer expectedRadius = 18854;

        BoundingBox boundingBox = aBoundingBox()
                .withMaxLatitude(maxLatitude)
                .withMaxLongitude(maxLongitude)
                .withMinLatitude(minLatitude)
                .withMinLongitude(minLongitude).build();

        Location location = new Location.Builder()
                .withLongitude(longitude)
                .withLatitude(latitude)
                .withBoundingBox(boundingBox).build();

        Integer distance = location.calculateRadius();
        assertEquals(expectedRadius, distance);
    }
}
