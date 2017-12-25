package com.weatherapp.model;

import lombok.Data;

@Data
public class List {

	private Main main;
	private Weather[] weather;
	private long dt;

	private List() {

	}
}
