package com.agentcoon.placefinder.api;

public class PlaceDto {

    private String name;
    private Float longitude;
    private Float latitude;

    private PlaceDto(String name, Float longitude, Float latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public static class PlaceBuilder {
        private String name;
        private Float longitude;
        private Float latitude;

        private PlaceBuilder() {}

        public static PlaceBuilder aPlace() {
            return new PlaceBuilder();
        }

        public PlaceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlaceBuilder withLongitude(Float longitude) {
            this.longitude = longitude;
            return this;
        }

        public PlaceBuilder withLatitude(Float latitude) {
            this.latitude = latitude;
            return this;
        }

        public PlaceDto build() {
            return new PlaceDto(name, longitude, latitude);
        }
    }
}
