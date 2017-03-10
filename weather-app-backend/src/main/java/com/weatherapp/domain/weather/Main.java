package com.weatherapp.domain.weather;

import lombok.Data;

@Data
public class Main {

	private double temp;
	private double temp_min;
	private double temp_max;
	private double pressure;
	private int humidity;

	private Main() {

	}

}
