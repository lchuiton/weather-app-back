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
import com.weatherapp.utils.exceptions.CityNotFoundException;

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
			@RequestParam String country) throws CityNotFoundException {
		String arguments = "&q=" + city + "," + country;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		System.out.println(request);

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
	public WeatherPrediction listPredictionParCoordonnees(@RequestParam String lat, @RequestParam String lng)
			throws CityNotFoundException {

		// Appel de l'API distante pour recuperer les informations
		String arguments = "&lat=" + lat + "&lon=" + lng;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		RestTemplate restTemplate = new RestTemplate();
		WeatherPrediction weather = restTemplate.getForObject(request, WeatherPrediction.class);

		if (StringUtils.isEmpty(weather.getCity().getName())) {
			DecimalFormat df = new DecimalFormat("####.##");
			df.setRoundingMode(RoundingMode.CEILING);
			weather.getCity()
					.setName("lat=" + df.format(Double.valueOf(lat)) + "\nlon=" + df.format(Double.valueOf(lng)));
			weather.getCity().setCountry("Unknown");
		}

		weather.calcMinMax();

		return weather;
	}

}
