package com.weatherapp.model;

import lombok.Getter;

@Getter
public class Temperature {

  private final String date;
  private final Double temp;

  public Temperature(String date, Double temp) {
    this.date = date;
    this.temp = temp;

  }

}
