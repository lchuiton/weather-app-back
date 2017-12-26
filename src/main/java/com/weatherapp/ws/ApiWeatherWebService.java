package com.weatherapp.ws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.weatherapp.model.WeatherPrediction;

public class ApiWeatherWebService {
	@Value("${api.weather.uri}")
	private String apiWeatherUri;

	@Autowired
	RestTemplate restTemplate;

	public WeatherPrediction callApiWeather(Map<String, String> uriVariables) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiWeatherUri);

		for (String variableKey : uriVariables.keySet()) {
			builder.queryParam(variableKey, uriVariables.get(variableKey));
		}

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<WeatherPrediction> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				entity, WeatherPrediction.class);

		return response.getBody();
	}

}
