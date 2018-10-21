package com.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main {

  private double temp;
  @JsonProperty("temp_min")
  private double tempMin;
  @JsonProperty("temp_max")
  private double tempMax;
  private double pressure;
  private int humidity;

}
