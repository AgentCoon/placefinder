package com.agentcoon.placefinder.domain.placefinder;

import java.util.Objects;

public class FacebookPlace {

    private final Float latitude;
    private final Float longitude;
    private final String name;
    private final String city;

    private FacebookPlace(Float latitude, Float longitude, String name, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.city = city;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
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
        private Float latitude;
        private Float longitude;
        private String name;
        private String city;

        public static Builder aFacebookPlace() {
            return new Builder();
        }

        public Builder withLatitude(Float latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Float longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public FacebookPlace build() {
            return new FacebookPlace(latitude, longitude, name, city);
        }
    }
}
