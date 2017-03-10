package com.weatherapp.utils;

public enum AddressTypes {
	//@formatter:off
	COUNTRY("country"), 
	CITY("locality");
	//@formatter:on

	private final String type;

	private AddressTypes(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
