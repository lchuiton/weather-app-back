package com.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WeatherPrediction {

  private City city;

  @Setter
  private List<Temperature> temperatureMinMax;
  @Setter
  private List<Temperature> temperatureMax;

  @JsonProperty("list")
  private List<ListOfValue> listOfValues;


}
