package com.weatherapp.helper;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.util.StringUtils;

import com.weatherapp.model.WeatherPrediction;

public class CoordinateHelper {

	public void formatCoordinate(WeatherPrediction weatherPrediction, String lat, String lng) {
		if (StringUtils.isEmpty(weatherPrediction.getCity().getName())) {
			String coordinate = formatCoordinate(lat, lng);
			weatherPrediction.getCity().setName(coordinate);
			weatherPrediction.getCity().setCountry("No country");
		}
	}

	private String formatCoordinate(String lat, String lng) {
		DecimalFormat df = new DecimalFormat("####.##");
		df.setRoundingMode(RoundingMode.CEILING);
		Double latitude = Double.valueOf(lat);
		Double longitude = Double.valueOf(lng);
		String displayLat = latitude < 0 ? df.format(Math.abs(latitude)) + "ºS" : df.format(latitude) + "ºN";
		String displayLon = longitude < 0 ? df.format(Math.abs(longitude)) + "ºO" : df.format(longitude) + "ºE";
		return String.format("%s, %s", displayLat, displayLon);
	}

}
