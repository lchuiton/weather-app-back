package com.weatherapp.model;

import lombok.Getter;

@Getter
public class TempMinMax {

  private final String date;
  private final Double maxTemp;
  private final Double minTemp;

  public TempMinMax(String date, Double maxTemp, Double minTemp) {
    this.date = date;
    this.maxTemp = maxTemp;
    this.minTemp = minTemp;

  }

}
