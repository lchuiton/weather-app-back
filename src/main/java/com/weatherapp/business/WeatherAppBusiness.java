package com.weatherapp.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherapp.helper.CoordinateHelper;
import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.model.WeatherPrediction;
import com.weatherapp.ws.ApiWeatherWebService;

@Service
public class WeatherAppBusiness {

	@Autowired
	private TemperatureHelper temperatureHelper;

	@Autowired
	private ApiWeatherWebService apiWeatherWebService;

	@Autowired
	private CoordinateHelper coordinateHelper;

	/**
	 * Returns a Weather forecast for a location, based on city and country
	 * names
	 * 
	 * @param city
	 *            the city's name for this research
	 * @param country
	 *            the country the city is in
	 * @return a {@link WeatherPredictionOutput} for this location
	 */
	public WeatherPrediction getForecastByCityAndCountry(String city, String country) {
		WeatherPrediction weatherPrediction = apiWeatherWebService.getPredictionByCityAndCountry(city, country);

		temperatureHelper.calcMinMax(weatherPrediction);

		return weatherPrediction;

	}

	/**
	 * Returns a Weather forecast for a location, based on coordinates
	 * 
	 * @param lat
	 *            the latitude of the location
	 * @param lng
	 *            the longitude of the location
	 * @return a {@link WeatherPredictionOutput} for this location
	 */
	public WeatherPrediction getForecastByCoordinates(String lat, String lng) {
		WeatherPrediction weatherPrediction = apiWeatherWebService.getPredictionByCoordinate(lat, lng);

		coordinateHelper.formatCoordinate(weatherPrediction, lat, lng);
		temperatureHelper.calcMinMax(weatherPrediction);

		return weatherPrediction;
	}

}
