package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherPrediction {

  private City city;
  private TempMinMax[] temperatureMinMax;
  private List[] list;
}
