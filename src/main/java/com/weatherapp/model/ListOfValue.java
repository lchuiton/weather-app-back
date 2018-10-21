package com.weatherapp.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListOfValue {

  private Main main;
  private List<Weather> weather;
  private long dt;

  private ListOfValue() {

  }
}
