package com.weatherapp.domain.weather;

import lombok.Data;

@Data
public class Coord {

	private float lon;
	private float lat;

	private Coord() {

	}
}
