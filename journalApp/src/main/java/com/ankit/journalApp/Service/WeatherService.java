package com.ankit.journalApp.Service;

import com.ankit.journalApp.api.response.WeatherResponse;
import com.ankit.journalApp.cache.AppCache;
import com.ankit.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private  String apiKey ;
//    private  static  final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
    String finalAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY,apiKey);
  ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.GET,null, com.ankit.journalApp.api.response.WeatherResponse.class);
  response.getStatusCode();
  WeatherResponse body=response.getBody();
  return body;
    }
}
