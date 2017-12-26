package com.weatherapp.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherapp.helper.CoordinateHelper;
import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.helper.WeatherSubscriber;
import com.weatherapp.model.WeatherPrediction;
import com.weatherapp.model.output.WeatherPredictionOutput;

@Service
public class WeatherAppBusiness {

	@Autowired
	private TemperatureHelper temperatureHelper;

	@Autowired
	private WeatherSubscriber weatherSubscriber;

	@Autowired
	private CoordinateHelper coordinateHelper;

	public WeatherPredictionOutput getForecastByCityAndCountry(String city, String country) {

		WeatherPrediction weatherPrediction = weatherSubscriber.getPredictionByCityAndCountry(city, country);

		temperatureHelper.calcMinMax(weatherPrediction);

		return buildWeatherPredictionOutput(weatherPrediction);

	}

	public WeatherPredictionOutput getForecastByCoordinates(String lat, String lng) {
		// Appel de l'API distante pour recuperer les informations

		WeatherPrediction weatherPrediction = weatherSubscriber.getPredictionByCoordinate(lat, lng);

		coordinateHelper.formatCoordinate(weatherPrediction, lat, lng);
		temperatureHelper.calcMinMax(weatherPrediction);

		return buildWeatherPredictionOutput(weatherPrediction);
	}

	private WeatherPredictionOutput buildWeatherPredictionOutput(WeatherPrediction weatherPrediction) {
		WeatherPredictionOutput output = WeatherPredictionOutput.builder().city(weatherPrediction.getCity())
				.temperatureMinMax(weatherPrediction.getTemperatureMinMax()).list(weatherPrediction.getList()).build();
		return output;
	}
}
