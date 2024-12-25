package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleService implements IGoogleService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public GoogleApiResponse getGeocodingData(String address) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                address,
                apiKey
        );
        return restTemplate.getForObject(url, GoogleApiResponse.class);
    }
}
