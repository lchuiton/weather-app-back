package com.weatherapp.helper;

import com.weatherapp.model.ListOfValue;
import com.weatherapp.model.Temperature;
import com.weatherapp.model.WeatherPrediction;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class TemperatureHelper {

  public List<Temperature> calculateMaximumTemperature(WeatherPrediction weatherPrediction) {
    List<ListOfValue> list = weatherPrediction.getListOfValues();

    Map<String, Double> test = list.stream()
        .map(a -> Pair.of(formatDateForDataFetching(a.getDt()), a.getMain().getTempMax()))
        .collect(Collectors.toMap(Pair::getLeft, Pair::getRight,
            (a, b) -> a < b ? b : a));

    List<Temperature> result = test.entrySet().stream()
        .map(a -> new Temperature(a.getKey(), a.getValue())).collect(
            Collectors.toList());

    result.sort(Comparator.comparing(Temperature::getDate));

    return result;
  }

  public List<Temperature> calculateMinimumTemperature(WeatherPrediction weatherPrediction) {
    List<ListOfValue> list = weatherPrediction.getListOfValues();

    Map<String, Double> test = list.stream()
        .map(a -> Pair.of(formatDateForDataFetching(a.getDt()), a.getMain().getTempMin()))
        .collect(Collectors.toMap(Pair::getLeft, Pair::getRight,
            (a, b) -> a > b ? b : a));

    List<Temperature> result = test.entrySet().stream()
        .map(a -> new Temperature(a.getKey(), a.getValue())).collect(
            Collectors.toList());
    result.sort(Comparator.comparing(Temperature::getDate));

    return result;
  }

  private String formatDateForDataFetching(long date) {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    cal.setTimeInMillis(date * 1000);
    StringBuilder dateDuJourBuilder = new StringBuilder();

    dateDuJourBuilder.append(cal.get(Calendar.YEAR)).append("-");

    int month = cal.get(Calendar.MONTH) + 1;

    if (month + 1 < 10) {
      dateDuJourBuilder.append(0);
    }
    dateDuJourBuilder.append(month).append("-");

    int day = cal.get(Calendar.DAY_OF_MONTH);

    if (day < 10) {
      dateDuJourBuilder.append(0);
    }
    dateDuJourBuilder.append(day);
    return dateDuJourBuilder.toString();
  }
}
