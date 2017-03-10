package com.weatherapp.utils.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = NOT_FOUND, reason = "No City found")
public class CityNotFoundException extends Exception {

	private String message;

	public CityNotFoundException(String message) {
		this.message = message;
	}

}