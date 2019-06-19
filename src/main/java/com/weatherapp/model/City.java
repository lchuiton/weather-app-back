package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {

  private String name;
  private String country;
  private Coord coord;

  private City() {}
}
