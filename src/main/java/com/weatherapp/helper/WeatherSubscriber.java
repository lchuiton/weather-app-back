package com.weatherapp.helper;

import org.springframework.beans.factory.annotation.Autowired;

import com.weatherapp.model.WeatherPrediction;
import com.weatherapp.ws.ApiWeatherWebService;

import io.reactivex.Single;

public class WeatherSubscriber {

	@Autowired
	private ApiWeatherWebService apiWeatherWebService;
	// FIXME I'm sure it's not supposed to work like that ...
	private WeatherPrediction weatherPrediction;

	public WeatherPrediction getPrediction(String request) {
		Single<WeatherPrediction> observer = Single.just(apiWeatherWebService.callApiWeather(request));
		observer.subscribe(s -> weatherPrediction = s);
		return weatherPrediction;
	}
}
