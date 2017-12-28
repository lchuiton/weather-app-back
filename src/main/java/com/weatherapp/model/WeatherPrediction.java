package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class WeatherPrediction {

	private City city;

	@Setter
	private TempMinMax[] temperatureMinMax;

	private List[] list;

	public WeatherPrediction() {
	}

}
