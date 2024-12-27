package com.weatherapp.geo_spring.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class GoogleApiResponse {
    private String status;
    private List<Result> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Geometry geometry;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geometry {
        private Location location;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Location {
        private double lat;
        private double lng;

    }

}
