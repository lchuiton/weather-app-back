package com.weatherapp.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.weatherapp.model.WeatherPrediction;
import com.weatherapp.ws.ApiWeatherWebService;

import io.reactivex.Single;

public class WeatherSubscriber {

	@Autowired
	private ApiWeatherWebService apiWeatherWebService;
	// FIXME I'm sure it's not suppose to work like that ...
	private WeatherPrediction weatherPrediction;

	public WeatherPrediction getPredictionByCoordinate(String lat, String lng) {
		Map<String, String> uriVariables = buildCoordinateRequest(lat, lng);
		getPrediction(uriVariables);
		return weatherPrediction;
	}

	public WeatherPrediction getPredictionByCityAndCountry(String cityName, String country) {
		Map<String, String> uriVariables = buildCityRequest(cityName, country);
		getPrediction(uriVariables);
		return weatherPrediction;
	}

	private Map<String, String> buildCoordinateRequest(String lat, String lng) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("lat", lat);
		uriVariables.put("lon", lng);

		return uriVariables;
	}

	private Map<String, String> buildCityRequest(String cityName, String country) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("q", cityName + "," + country);

		return uriVariables;
	}

	private void getPrediction(Map<String, String> uriVariables) {
		Single<WeatherPrediction> observer = Single.just(apiWeatherWebService.callApiWeather(uriVariables));
		observer.subscribe(s -> weatherPrediction = s);
	}

}
