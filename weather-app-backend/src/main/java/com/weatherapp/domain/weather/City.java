package com.weatherapp.domain.weather;

import lombok.Data;

@Data
public class City {

	private String name;
	private String country;
	private Coord coord;

	private City() {

	}
}
