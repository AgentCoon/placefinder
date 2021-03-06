package com.agentcoon.placefinder.mapquest.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationResponseDto {

    @JsonProperty("lat")
    private Float latitude;

    @JsonProperty("lon")
    private Float longitude;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("boundingbox")
    private List<Float> boundingBox;

    public GeoLocationResponseDto() {}

    public GeoLocationResponseDto(Float latitude, Float longitude, String displayName, String type, List<Float> boundingBox) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayName = displayName;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public List<Float> getBoundingBox() {
        return boundingBox;
    }

    public static final class Builder {
        private Float latitude;
        private Float longitude;
        private String displayName;
        private String type;
        private List<Float> boundingBox;

        public static Builder aNominatimResponse() {
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

        public Builder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withBoundingBox(List<Float> boundingBox) {
            this.boundingBox = boundingBox;
            return this;
        }

        public GeoLocationResponseDto build() {
            return new GeoLocationResponseDto(latitude, longitude, displayName, type, boundingBox);
        }
    }
}
