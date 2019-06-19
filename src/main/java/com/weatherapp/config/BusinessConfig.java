package com.weatherapp.config;

import com.weatherapp.helper.CoordinateHelper;
import com.weatherapp.helper.TemperatureHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BusinessConfig {

  @Bean
  TemperatureHelper temperatureHelper() {
    return new TemperatureHelper();
  }

  @Bean
  CoordinateHelper coordinateHelper() {
    return new CoordinateHelper();
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
