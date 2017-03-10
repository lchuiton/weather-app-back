package com.weatherapp.domain.coordinates;

import lombok.Data;

@Data
public class Results {

	private AddressComponents[] address_components;
	private String[] types;

	private Results() {

	}
}
