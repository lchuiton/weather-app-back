package com.weatherapp.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.domain.WeatherPrediction;

@RestController
public class HomeController {

	@Value("${api.weather.url}")
	private String urlWeatherAPI;
	@Value("${api.weather.key}")
	private String keyWeatherAPI;

	/**
	 * Take a <b>city</b> and a <b>country</b> as parameters and will call the weather API to get forecast on the five
	 * next days.
	 * 
	 * @param city
	 * @param country
	 * @return WeatherPrediction
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/byCityAndCountry")
	public WeatherPrediction listPredictionParNomDeVilleEtDePays(@RequestParam String city,
			@RequestParam String country) {
		String arguments = "&q=" + city + "," + country;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		RestTemplate restTemplate = new RestTemplate();
		WeatherPrediction weather = restTemplate.getForObject(request, WeatherPrediction.class);

		weather.calcMinMax();

		return weather;
	}

	/**
	 * Take a <b>lat</b> and a <b>lng</b> as parameters and will call the weather API to get forecast on the five next
	 * days.
	 * 
	 * @param lat
	 * @param lng
	 * @return WeatherPrediction
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/byCoordinates")
	public WeatherPrediction listPredictionParCoordonnees(@RequestParam String lat, @RequestParam String lng) {

		// Appel de l'API distante pour recuperer les informations
		String arguments = "&lat=" + lat + "&lon=" + lng;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		RestTemplate restTemplate = new RestTemplate();
		WeatherPrediction weather = restTemplate.getForObject(request, WeatherPrediction.class);

		if (StringUtils.isEmpty(weather.getCity().getName())) {
			String coordinate = formatCoordinate(lat, lng);
			weather.getCity().setName(coordinate);

			weather.getCity().setCountry("No country");
		}

		weather.calcMinMax();

		return weather;
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

}
