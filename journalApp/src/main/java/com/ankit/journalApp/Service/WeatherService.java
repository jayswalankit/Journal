package com.ankit.journalApp.Service;

import com.ankit.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private  static  final String apiKey = "a21cdf8ae4e549e7c00fb16d7af8361d";
    private  static  final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
    String finalAPI=API.replace("CITY",city).replace("API_KEY",apiKey);
  ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.GET,null, com.ankit.journalApp.api.response.WeatherResponse.class);
  response.getStatusCode();
  WeatherResponse body=response.getBody();
  return body;
    }
}
