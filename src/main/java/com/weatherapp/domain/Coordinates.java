package com.weatherapp.domain;

import java.util.Arrays;
import java.util.List;

import com.weatherapp.domain.coordinates.AddressComponents;
import com.weatherapp.domain.coordinates.Results;
import com.weatherapp.utils.AddressTypes;

import lombok.Data;

@Data
public class Coordinates {

	private Results[] results;

	private String status;

	private Coordinates() {

	}

	public String getCity() {
		AddressComponents city = searchType(AddressTypes.CITY);
		return city != null ? city.getLong_name() : "";
	}

	public String getCountry() {
		AddressComponents country = searchType(AddressTypes.COUNTRY);
		return country != null ? country.getShort_name() : "";
	}

	private AddressComponents searchType(AddressTypes addressTypes) {
		AddressComponents addressComponentResult = null;
		for (Results result : results) {
			List<String> list = Arrays.asList(result.getTypes());

			if (list.contains(addressTypes.getType())) {
				addressComponentResult = result.getAddress_components()[0];
			}
		}
		return addressComponentResult;

	}

}
