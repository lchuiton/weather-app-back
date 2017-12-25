package com.weatherapp.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.model.WeatherPrediction;

public class ApiWeatherWebService {

	@Autowired
	RestTemplate restTemplate;

	public WeatherPrediction callApiWeather(String request) {
		return restTemplate.getForObject(request, WeatherPrediction.class);
	}
}
