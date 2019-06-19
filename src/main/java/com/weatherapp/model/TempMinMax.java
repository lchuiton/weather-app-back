package com.weatherapp.model;

import lombok.Getter;

@Getter
public class TempMinMax {

  private String date;
  private Double maxTemp;
  private Double minTemp;

  public TempMinMax(String date, Double maxTemp, Double minTemp) {
    this.date = date;
    this.maxTemp = maxTemp;
    this.minTemp = minTemp;
  }
}
