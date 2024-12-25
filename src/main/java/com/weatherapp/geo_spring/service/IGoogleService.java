package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;

public interface IGoogleService {

    GoogleApiResponse getGeocodingData(String address);
}
