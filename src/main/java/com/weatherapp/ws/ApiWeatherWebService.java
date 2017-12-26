package com.weatherapp.ws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.model.WeatherPrediction;

public class ApiWeatherWebService {
	@Value("${api.weather.uri}")
	private String apiWeatherUri;

	@Autowired
	RestTemplate restTemplate;

	public WeatherPrediction callApiWeather(Map<String, String> uriVariables) {
		String request = buildRequestUri(uriVariables);

		return restTemplate.getForObject(request, WeatherPrediction.class);
	}

	private String buildRequestUri(Map<String, String> uriVariables) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(apiWeatherUri);

		if (!uriVariables.isEmpty()) {
			stringBuilder.append("&");
		}

		for (String variableKey : uriVariables.keySet()) {
			stringBuilder.append(variableKey);
			stringBuilder.append("=");
			stringBuilder.append(uriVariables.get(variableKey));
		}

		return stringBuilder.toString();

	}

}
