package com.agentcoon.placefinder.domain.geolocation;

public class BoundingBox {

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
