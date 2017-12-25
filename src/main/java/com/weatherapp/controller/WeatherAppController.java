package com.weatherapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.business.WeatherAppBusiness;
import com.weatherapp.model.WeatherPrediction;

@RestController
public class WeatherAppController {

	@Autowired
	WeatherAppBusiness business;

	/**
	 * Take a <b>city</b> and a <b>country</b> as parameters and will call the
	 * weatherPrediction API to get forecast on the five next days.
	 * 
	 * @param city
	 * @param country
	 * @return WeatherPrediction
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/byCityAndCountry")
	public WeatherPrediction listPredictionParNomDeVilleEtDePays(@RequestParam String city,
			@RequestParam String country) {

		return business.getForecastByCityAndCountry(city, country);

	}

	/**
	 * Take a <b>lat</b> and a <b>lng</b> as parameters and will call the
	 * weatherPrediction API to get forecast on the five next days.
	 * 
	 * @param lat
	 * @param lng
	 * @return WeatherPrediction
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/byCoordinates")
	public WeatherPrediction listPredictionParCoordonnees(@RequestParam String lat, @RequestParam String lng) {

		return business.getForecastByCoordinates(lat, lng);

	}

	@ExceptionHandler({ Exception.class })
	public String exceptionHandler(HttpServletRequest req, Exception ex) {
		return ex.getMessage();
	}

}
