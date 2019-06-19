package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class List {

    private Main main;
    private Weather[] weather;
    private long dt;

    private List() {

    }
}
