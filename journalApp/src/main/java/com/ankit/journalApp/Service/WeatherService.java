package com.ankit.journalApp.Service;

import com.ankit.journalApp.api.response.WeatherResponse;
import com.ankit.journalApp.cache.AppCache;
import com.ankit.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        System.out.println(" Checking Redis for: " + city);

     WeatherResponse weatherResponse =  redisService.get("weather of " + city , WeatherResponse.class);
     if(weatherResponse!=null){
         System.out.println(" CACHE HIT");
         return weatherResponse;
     }
     else{
         System.out.println(" CACHE MISS → API call hogi");

         String finalAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY,apiKey); // isme db se data nhi cache se aa rha hai ....
         ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.GET,null, com.ankit.journalApp.api.response.WeatherResponse.class);
         response.getStatusCode();
         WeatherResponse body=response.getBody();

         if(body!=null){
             System.out.println(" Saving to Redis...");
             redisService.set("weather of " + city,body,300l);
         }
         return body;
     }

    }
}
