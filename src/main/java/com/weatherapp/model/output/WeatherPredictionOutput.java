package com.weatherapp.model.output;

import com.weatherapp.model.City;
import com.weatherapp.model.List;
import com.weatherapp.model.TempMinMax;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherPredictionOutput {

	private City city;
	private TempMinMax[] temperatureMinMax;
	private List[] list;

}
