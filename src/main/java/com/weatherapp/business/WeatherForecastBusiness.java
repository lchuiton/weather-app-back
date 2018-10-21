package com.weatherapp.business;

import com.weatherapp.helper.CoordinateHelper;
import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.model.WeatherPrediction;
import com.weatherapp.ws.ApiWeatherWebService;
import org.springframework.stereotype.Service;

@Service
public class WeatherForecastBusiness {

  private TemperatureHelper temperatureHelper;

  private ApiWeatherWebService apiWeatherWebService;

  private CoordinateHelper coordinateHelper;

  public WeatherForecastBusiness(TemperatureHelper temperatureHelper, CoordinateHelper coordinateHelper, ApiWeatherWebService apiWeatherWebService) {
    this.apiWeatherWebService = apiWeatherWebService;
    this.temperatureHelper = temperatureHelper;
    this.coordinateHelper = coordinateHelper;
  }

  /**
   * Returns a Weather forecast for a location, based on city and country names
   *
   * @param city the city's name for this research
   * @param country the country the city is in
   * @return a {@link WeatherPrediction} for this location
   */
  public WeatherPrediction getForecastByCityAndCountry(String city, String country) {
    WeatherPrediction weatherPrediction = apiWeatherWebService.getPredictionByCityAndCountry(city, country);

    if (weatherPrediction != null) {
      weatherPrediction.setTemperatureMinMax(temperatureHelper.calculateMinimumTemperature(weatherPrediction));
      weatherPrediction.setTemperatureMax(temperatureHelper.calculateMaximumTemperature(weatherPrediction));
    }

    return weatherPrediction;

  }

  /**
   * Returns a Weather forecast for a location, based on coordinates
   *
   * @param lat the latitude of the location
   * @param lng the longitude of the location
   * @return a {@link WeatherPrediction} for this location
   */
  public WeatherPrediction getForecastByCoordinates(String lat, String lng) {
    WeatherPrediction weatherPrediction = apiWeatherWebService.getPredictionByCoordinate(lat, lng);

    coordinateHelper.formatCoordinate(weatherPrediction, lat, lng);

    weatherPrediction.setTemperatureMinMax(temperatureHelper.calculateMinimumTemperature(weatherPrediction));
    weatherPrediction.setTemperatureMax(temperatureHelper.calculateMaximumTemperature(weatherPrediction));

    return weatherPrediction;
  }

}
