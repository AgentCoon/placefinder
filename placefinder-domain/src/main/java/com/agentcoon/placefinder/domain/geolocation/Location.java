package com.agentcoon.placefinder.domain.geolocation;

import static org.apache.commons.lang3.math.NumberUtils.max;

public class Location {

    private static final int EARTH_RADIUS_IN_M = 6371000;

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

        Double a = distance(latitude, longitude, boundingBox.getMaxLatitude(), boundingBox.getMaxLongitude());
        Double b = distance(latitude, longitude, boundingBox.getMaxLatitude(), boundingBox.getMinLongitude());
        Double c = distance(latitude, longitude, boundingBox.getMinLatitude(), boundingBox.getMaxLongitude());
        Double d = distance(latitude, longitude, boundingBox.getMinLatitude(), boundingBox.getMinLongitude());

        Double maxRadius = max(a, b, c, d, 1000);

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
