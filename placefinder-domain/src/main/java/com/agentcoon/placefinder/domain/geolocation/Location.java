package com.agentcoon.placefinder.domain.geolocation;

import static java.lang.Math.max;

public class Location {

    private static final Integer DEFAULT_RADIUS_IN_M = 3000;

    private final Float latitude;
    private final Float longitude;
    private final String displayName;
    private final BoundingBox boundingBox;

    private Location(Float latitude, Float longitude, String displayName, BoundingBox boundingBox) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayName = displayName;
        this.boundingBox = boundingBox;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public Integer calculateRadius() {

        Integer maxRadius = DEFAULT_RADIUS_IN_M;

        if(boundingBox != null) {
             maxRadius = max(boundingBox.calculateFarthestVertexFrom(latitude, longitude), DEFAULT_RADIUS_IN_M);
        }

        return maxRadius;
    }

    public static final class Builder {
        private Float longitude;
        private Float latitude;
        private String displayName;
        private BoundingBox boundingBox;

        public static Builder aLocation() {
            return new Builder();
        }

        public Builder withLongitude(Float longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withLatitude(Float latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder withBoundingBox(BoundingBox boundingBox) {
            this.boundingBox = boundingBox;
            return this;
        }

        public Location build() {
            return new Location(latitude, longitude, displayName, boundingBox);
        }
    }
}
