package com.weatherapp.controller;

import com.weatherapp.business.WeatherAppService;
import com.weatherapp.model.WeatherPrediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WeatherAppController {

  private WeatherAppService business;

  @Autowired
  public WeatherAppController(WeatherAppService business) {
    this.business = business;
  }

  /**
   * Take a <b>city</b> and a <b>country</b> as parameters and will call the weatherPrediction API
   * to get forecast on the five next days.
   *
   * @param city the city for which we want the forecast
   * @param country the city's country
   * @return WeatherPrediction
   */
  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(value = "/byCityAndCountry")
  public Mono<WeatherPrediction> listPredictionParNomDeVilleEtDePays(
      @RequestParam String city, @RequestParam String country) {
    //		Mono<WeatherPrediction> observable =
    return Mono.just(business.getForecastByCityAndCountry(city, country));
    //		DeferredResult<WeatherPrediction> deffered = new DeferredResult<WeatherPrediction>();
    //		observable.subscribe(m -> deffered.setResult(m), e -> deffered.setErrorResult(e));
    //		return deffered;

  }

  /**
   * Take a <b>lat</b> and a <b>lng</b> as parameters and will call the weatherPrediction API to get
   * forecast on the five next days.
   *
   * @param lat latitude of the coordinate
   * @param lng longitude of the coordinate
   * @return WeatherPrediction
   */
  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(value = "/byCoordinates")
  public WeatherPrediction listPredictionParCoordonnees(
      @RequestParam String lat, @RequestParam String lng) {

    return business.getForecastByCoordinates(lat, lng);
  }

  @ExceptionHandler({Exception.class})
  public String exceptionHandler(HttpServletRequest req, Exception ex) {
    return ex.getMessage();
  }
}
