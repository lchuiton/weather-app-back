package com.weatherapp.controller;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.domain.Coordinates;
import com.weatherapp.domain.WeatherPrediction;
import com.weatherapp.utils.exceptions.CityNotFoundException;

@RestController
public class HomeController {

	@Value("${api.weather.url}")
	private String urlWeatherAPI;
	@Value("${api.weather.key}")
	private String keyWeatherAPI;
	@Value("${api.googlemaps.url}")
	private String urlGoogleMapsAPI;
	@Value("${api.googlemaps.key}")
	private String keyGoogleMaps;

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/byCityAndCountry")
	public WeatherPrediction listPredictionParNomDeVilleEtDePays(
			@RequestParam String city, @RequestParam String country)
			throws CityNotFoundException {
		return getWeatherPredictionFromAPI(city, country);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/byCoordinates")
	public WeatherPrediction listPredictionParCoordonnees(@RequestParam String lat,
			@RequestParam String lng) throws CityNotFoundException {

		// Appel de l'API distante pour recuperer les informations nom de ville et de pays
		String arguments = "&latlng=" + lat + "," + lng;
		String request = urlGoogleMapsAPI + keyGoogleMaps + arguments;
		System.out.println(request);

		RestTemplate restTemplate = new RestTemplate();
		Coordinates coordinates = restTemplate.getForObject(request, Coordinates.class);

		String city = coordinates.getCity();
		String country = coordinates.getCountry();

		if (isEmpty(city)) {
			throw new CityNotFoundException("No city found for these coordinates");
		}

		return getWeatherPredictionFromAPI(city, country);
	}

	// Appel de l'API distante pour recuperer les informations meteorologiques
	/**
	 * Take a <b>city</b> and a <b>country</b> as parameters and will call the weather API to get forecast on the five
	 * next days.
	 * 
	 * @param city
	 * @param country
	 * @return WeatherPrediction
	 */
	private WeatherPrediction getWeatherPredictionFromAPI(String city, String country) {

		String arguments = "&q=" + city + "," + country;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		System.out.println(request);

		RestTemplate restTemplate = new RestTemplate();
		WeatherPrediction weather = restTemplate.getForObject(request,
				WeatherPrediction.class);

		weather.calcMinMax();

		return weather;
	}

}
