package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class GoogleServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleService googleService;

    @Test
    public void getGeocodingData_ShouldReturnGoogleApiResponse() {

        String address = "Test Address";
        String apiKey = "dummyApiKey";

        ReflectionTestUtils.setField(googleService, "apiKey", apiKey);

        String expectedUrl = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                address,
                apiKey
        );

        GoogleApiResponse googleApiResponse = new GoogleApiResponse();
        googleApiResponse.setResults(Arrays.asList(
                new GoogleApiResponse.Result(new GoogleApiResponse.Geometry(new GoogleApiResponse.Location(40.0, 30.0)))
        ));

        when(restTemplate.getForObject(expectedUrl, GoogleApiResponse.class)).thenReturn(googleApiResponse);

        GoogleApiResponse response = googleService.getGeocodingData(address);

        verify(restTemplate, times(1)).getForObject(expectedUrl, GoogleApiResponse.class);
        assertNotNull(response);
        assertEquals(40.0, response.getResults().get(0).getGeometry().getLocation().getLat());
        assertEquals(30.0, response.getResults().get(0).getGeometry().getLocation().getLng());
    }

    @Test
    public void calculateDistance_ShouldReturnCorrectDistance() {

        double lat1 = 40.0;
        double lon1 = 29.0;
        double lat2 = 41.0;
        double lon2 = 30.0;

        double distance = googleService.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals(139.17, distance, 0.9);
    }
}
