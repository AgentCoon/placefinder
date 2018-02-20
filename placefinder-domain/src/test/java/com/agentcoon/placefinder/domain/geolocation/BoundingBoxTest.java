package com.agentcoon.placefinder.domain.geolocation;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class BoundingBoxTest {

    @Test
    @UseDataProvider("radiusCalculator")
    public void calculateDistance(Float latitude, Float longitude, Float maxLatitude, Float minLatitude, Float maxLongitude, Float minLongitude, Integer expectedRadius) {

        BoundingBox boundingBox = new BoundingBox.Builder()
                .withMaxLatitude(maxLatitude)
                .withMinLatitude(minLatitude)
                .withMaxLongitude(maxLongitude)
                .withMinLongitude(minLongitude).build();

        Integer radius = boundingBox.calculateFarthestVertexFrom(latitude, longitude);
        assertEquals(expectedRadius, radius);
    }

    @DataProvider
    public static Object[][] radiusCalculator() {

        return new Object[][] {
                { 15.78f, 30.98f, 20.65f, 16.45f, 35.98f, 33.78f, 756246 },
                { -15.78f, -30.98f, -20.65f, -16.45f, -35.98f, -33.78f, 756246 },
                { -35.29f, 149.10f, -35.46f, -35.14f, 149.26f, 148.94f, 23827 },
        };
    }
}
