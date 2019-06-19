package com.weatherapp.config;

import com.weatherapp.helper.CoordinateHelper;
import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.ws.ApiWeatherWebService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BusinessConfig {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  TemperatureHelper temperatureHelper() {
    return new TemperatureHelper();
  }

  @Bean
  ApiWeatherWebService apiWeatherWebService() {
    return new ApiWeatherWebService();
  }

  @Bean
  CoordinateHelper coordinateHelper() {
    return new CoordinateHelper();
  }
}
