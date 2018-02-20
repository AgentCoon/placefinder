package com.agentcoon.placefinder.domain.geolocation;

import static org.apache.commons.lang3.math.NumberUtils.max;

public class BoundingBox {

    private static final int EARTH_RADIUS_IN_M = 6371000;

    private final Float minLongitude;
    private final Float minLatitude;
    private final Float maxLongitude;
    private final Float maxLatitude;

    private BoundingBox(Float minLongitude, Float minLatitude, Float maxLongitude, Float maxLatitude) {
        this.minLongitude = minLongitude;
        this.minLatitude = minLatitude;
        this.maxLongitude = maxLongitude;
        this.maxLatitude = maxLatitude;
    }

    public Float getMinLongitude() {
        return minLongitude;
    }

    public Float getMinLatitude() {
        return minLatitude;
    }

    public Float getMaxLongitude() {
        return maxLongitude;
    }

    public Float getMaxLatitude() {
        return maxLatitude;
    }

    public Integer calculateFarthestVertexFrom(Float latitude, Float longitude) {

        Double a = distance(latitude, longitude, maxLatitude, maxLongitude);
        Double b = distance(latitude, longitude, maxLatitude, minLongitude);
        Double c = distance(latitude, longitude, minLatitude, maxLongitude);
        Double d = distance(latitude, longitude, minLatitude, minLongitude);

        Double maxRadius = max(a, b, c, d);

        return maxRadius.intValue();
    }

    private double distance(Float lat1, Float lon1, Float lat2, Float lon2) {
        double dLat = deg2rad(lat2-lat1);
        double dLon = deg2rad(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS_IN_M * c;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static final class Builder {
        private Float minLongitude;
        private Float minLatitude;
        private Float maxLongitude;
        private Float maxLatitude;

        public static Builder aBoundingBox() {
            return new Builder();
        }

        public Builder withMinLongitude(Float minLongitude) {
            this.minLongitude = minLongitude;
            return this;
        }

        public Builder withMinLatitude(Float minLatitude) {
            this.minLatitude = minLatitude;
            return this;
        }

        public Builder withMaxLongitude(Float maxLongitude) {
            this.maxLongitude = maxLongitude;
            return this;
        }

        public Builder withMaxLatitude(Float maxLatitude) {
            this.maxLatitude = maxLatitude;
            return this;
        }

        public BoundingBox build() {
            return new BoundingBox(minLongitude, minLatitude, maxLongitude, maxLatitude);
        }
    }
}
