package com.weatherapp.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.domain.WeatherPrediction;

import io.reactivex.Single;

@RestController
public class HomeController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${api.weather.url}")
	private String urlWeatherAPI;
	@Value("${api.weather.key}")
	private String keyWeatherAPI;

	WeatherPrediction weatherPrediction;

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
		String arguments = "&q=" + city + "," + country;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		System.out.println("1");
		getPrediction(request);
		System.out.println("2");

		weatherPrediction.calcMinMax();

		return weatherPrediction;
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

		// Appel de l'API distante pour recuperer les informations
		String arguments = "&lat=" + lat + "&lon=" + lng;
		String request = urlWeatherAPI + keyWeatherAPI + arguments;

		System.out.println("1");
		getPrediction(request);
		System.out.println("2");
		if (StringUtils.isEmpty(weatherPrediction.getCity().getName())) {
			String coordinate = formatCoordinate(lat, lng);
			weatherPrediction.getCity().setName(coordinate);

			weatherPrediction.getCity().setCountry("No country");
		}

		weatherPrediction.calcMinMax();

		return weatherPrediction;
	}

	private void getPrediction(String request) {
		Single<WeatherPrediction> observer = Single.just(callApiWeather(request)); // provides
																					// datea
		observer.subscribe(s -> weatherPrediction = s); // Callable as
														// subscriber
	}

	private WeatherPrediction callApiWeather(String request) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restTemplate.getForObject(request, WeatherPrediction.class);
	}

	@ExceptionHandler({ Exception.class })
	public String exceptionHandler(HttpServletRequest req, Exception ex) {
		// logger.error("Request: " + req.getRequestURL() + " raised " + ex);
		// Nothing to do. Returns the logical view name of an error page, passed
		// to the view-resolver(s) in usual way.
		// Note that the exception is NOT available to this view (it is not
		// added
		// to the model) but see "Extending ExceptionHandlerExceptionResolver"
		// below.
		return ex.getMessage();
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

	// private WeatherPrediction test(String request) {
	// Flowable.fromCallable(() -> {
	// return "Done";
	// }).subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe(System.out::println,
	// Throwable::printStackTrace);
	//
	// Single<WeatherPrediction> todosSingle = Single.create(emitter -> {
	// Thread thread = new Thread(() -> {
	// try {
	// WeatherPrediction todosFromWeb = restTemplate.getForObject(request,
	// WeatherPrediction.class);
	// System.out.println("I am only called once!");
	//
	// emitter.onSuccess(todosFromWeb);
	// } catch (Exception e) {
	// emitter.onError(e);
	// }
	// });
	// thread.start();
	// });
	//
	// Single<WeatherPrediction> cachedSingle = todosSingle.cache();
	//
	// cachedSingle.subscribe();
	// cachedSingle.subscribe();
	// cachedSingle.subscribe();
	// cachedSingle.subscribe();
	// return (WeatherPrediction) todosSingle.subscribe();
	// }

}
