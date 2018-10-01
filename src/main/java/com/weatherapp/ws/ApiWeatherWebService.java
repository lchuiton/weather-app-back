package com.weatherapp.ws;

import com.weatherapp.model.WeatherPrediction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ApiWeatherWebService {

  @Autowired
  private
  RestTemplate restTemplate;
  @Value("${api.weather.uri}")
  private String apiWeatherUri;

  public WeatherPrediction getPredictionByCoordinate(String lat, String lng) {
    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("lat", lat);
    uriVariables.put("lon", lng);
    return callApiWeather(uriVariables);
  }

  public WeatherPrediction getPredictionByCityAndCountry(String cityName, String country) {
    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("q", cityName + "," + country);
    return callApiWeather(uriVariables);
  }

  private WeatherPrediction callApiWeather(Map<String, String> uriVariables) {

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiWeatherUri);

    for (String variableKey : uriVariables.keySet()) {
      builder.queryParam(variableKey, uriVariables.get(variableKey));
    }

    HttpEntity<?> entity = new HttpEntity<>(headers);

    HttpEntity<WeatherPrediction> response = restTemplate
        .exchange(builder.build().encode().toUri(), HttpMethod.GET,
            entity, WeatherPrediction.class);

    return response.getBody();
  }

}
