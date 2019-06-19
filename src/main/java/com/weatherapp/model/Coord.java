package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coord {

  private float lon;
  private float lat;

  private Coord() {}
}
