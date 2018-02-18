package com.agentcoon.placefinder.domain.placefinder;

import java.util.Objects;

public class FacebookPlace {

    private final Double latitude;
    private final Double longitude;
    private final String displayName;

    private FacebookPlace(Double latitude, Double longitude, String displayName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayName = displayName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacebookPlace that = (FacebookPlace) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(displayName, that.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, displayName);
    }

    public static final class Builder {
        private Double latitude;
        private Double longitude;
        private String displayName;

        public static Builder aPlace() {
            return new Builder();
        }

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public FacebookPlace build() {
            return new FacebookPlace(latitude, longitude, displayName);
        }
    }
}
