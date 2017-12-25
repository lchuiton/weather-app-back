package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class WeatherPrediction {

	private City city;
	private List[] list;

	@Setter
	private TempMinMax[] temperatureMinMax;

	public WeatherPrediction() {
	}

}
