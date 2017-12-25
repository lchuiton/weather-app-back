package com.weatherapp.business;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.helper.WeatherSubscriber;
import com.weatherapp.model.WeatherPrediction;

@Service
public class WeatherAppBusiness {

	@Value("${api.weather.url}")
	private String urlWeatherAPI;
	@Value("${api.weather.key}")
	private String keyWeatherAPI;

	@Autowired
	private TemperatureHelper temperatureHelper;

	@Autowired
	private WeatherSubscriber weatherSubscriber;

	public WeatherPrediction getForecastByCityAndCountry(String city, String country) {
		String arguments = "&q=" + city + "," + country;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		WeatherPrediction weatherPrediction = weatherSubscriber.getPrediction(request);

		temperatureHelper.calcMinMax(weatherPrediction);

		return weatherPrediction;
	}

	private String formatCoordinate(String lat, String lng) {
		DecimalFormat df = new DecimalFormat("####.##");
		df.setRoundingMode(RoundingMode.CEILING);
		Double latitude = Double.valueOf(lat);
		Double longitude = Double.valueOf(lng);
		String displayLat = latitude < 0 ? df.format(Math.abs(latitude)) + "ºS" : df.format(latitude) + "ºN";
		String displayLon = longitude < 0 ? df.format(Math.abs(longitude)) + "ºO" : df.format(longitude) + "ºE";
		return displayLat + ", " + displayLon;
	}

	public WeatherPrediction getForecastByCoordinates(String lat, String lng) {
		// Appel de l'API distante pour recuperer les informations
		String arguments = "&lat=" + lat + "&lon=" + lng;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;
		WeatherPrediction weatherPrediction = weatherSubscriber.getPrediction(request);
		if (StringUtils.isEmpty(weatherPrediction.getCity().getName())) {
			String coordinate = formatCoordinate(lat, lng);
			weatherPrediction.getCity().setName(coordinate);
			weatherPrediction.getCity().setCountry("No country");
		}

		temperatureHelper.calcMinMax(weatherPrediction);

		return weatherPrediction;
	}
}
