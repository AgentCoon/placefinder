package com.agentcoon.placefinder.api;

public class PlaceDto {

    private String name;
    private Double longitude;
    private Double latitude;

    public PlaceDto() {
    }

    public PlaceDto(String name, Double longitude, Double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public static class PlaceBuilder {
        private String name;
        private Double longitude;
        private Double latitude;

        private PlaceBuilder() {}

        public static PlaceBuilder aPlace() {
            return new PlaceBuilder();
        }

        public PlaceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlaceBuilder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public PlaceBuilder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public PlaceDto build() {
            return new PlaceDto(name, longitude, latitude);
        }
    }
}
