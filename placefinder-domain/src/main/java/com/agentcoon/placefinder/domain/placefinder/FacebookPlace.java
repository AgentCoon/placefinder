package com.agentcoon.placefinder.domain.placefinder;

import java.util.Objects;

public class FacebookPlace {

    private final Double latitude;
    private final Double longitude;
    private final String name;

    private FacebookPlace(Double latitude, Double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacebookPlace that = (FacebookPlace) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, name);
    }

    public static final class Builder {
        private Double latitude;
        private Double longitude;
        private String name;

        public static Builder aFacebookPlace() {
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

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public FacebookPlace build() {
            return new FacebookPlace(latitude, longitude, name);
        }
    }
}
