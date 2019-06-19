package com.weatherapp.controller;

import com.weatherapp.business.WeatherAppService;
import com.weatherapp.model.WeatherPrediction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WeatherAppController {

  private Logger logger = LoggerFactory.getLogger(this.getClass().toString());

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
  public Mono<WeatherPrediction> weatherPredictionByCityAndCountry(
      @RequestParam String city, @RequestParam String country) {
    return Mono.just(business.getForecastByCityAndCountry(city, country));
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
  public Mono<WeatherPrediction> weatherPredictionByCoordinates(
      @RequestParam String lat, @RequestParam String lng) {

    return Mono.just(business.getForecastByCoordinates(lat, lng));
  }

  @ExceptionHandler({Exception.class})
  public String exceptionHandler(HttpServletRequest req, Exception ex) {
    logger.error("Error for request {}", req.getRequestURI());
    return ex.getMessage();
  }
}
