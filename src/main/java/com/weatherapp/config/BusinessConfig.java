package com.weatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.helper.CoordinateHelper;
import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.helper.WeatherSubscriber;
import com.weatherapp.ws.ApiWeatherWebService;

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
	WeatherSubscriber weatherSubscriber() {
		return new WeatherSubscriber();
	}

	@Bean
	CoordinateHelper coordinateHelper() {
		return new CoordinateHelper();
	}

}
