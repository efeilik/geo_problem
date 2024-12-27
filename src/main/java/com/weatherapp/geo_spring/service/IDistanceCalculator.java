package com.weatherapp.geo_spring.service;

public interface IDistanceCalculator {
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
