package com.weatherapp.model;

import lombok.Data;

@Data
public class WeatherPrediction {

	private City city;
	private List[] list;
	private TempMinMax[] temperatureMinMax;

	public WeatherPrediction() {
	}

}
