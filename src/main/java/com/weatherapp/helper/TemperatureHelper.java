package com.weatherapp.helper;

import com.weatherapp.model.List;
import com.weatherapp.model.TempMinMax;
import com.weatherapp.model.WeatherPrediction;

import java.util.*;

public class TemperatureHelper {

  public void calcMinMax(WeatherPrediction weatherPrediction) {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    Map<String, Double[]> map = new HashMap<>();

    List[] list = weatherPrediction.getList();

    for (List listDeData : list) {
      cal.setTimeInMillis(listDeData.getDt() * 1000);
      StringBuilder dateDuJourBuilder = new StringBuilder();

      dateDuJourBuilder.append(cal.get(Calendar.YEAR)).append("-");

      int mois = cal.get(Calendar.MONTH) + 1;

      if (mois + 1 < 10) {
        dateDuJourBuilder.append(0);
      }
      dateDuJourBuilder.append(mois).append("-");

      int jour = cal.get(Calendar.DAY_OF_MONTH);

      if (jour < 10) {
        dateDuJourBuilder.append(0);
      }
      dateDuJourBuilder.append(jour);

      Double tempMax = listDeData.getMain().getTempMax();
      Double tempMin = listDeData.getMain().getTempMin();

      String dateDuJour = dateDuJourBuilder.toString();
      if (!map.containsKey(dateDuJour)) {
        map.put(dateDuJour, new Double[] {tempMax, tempMin});
      } else {
        Double tempMaxInit = map.get(dateDuJour)[0];
        Double tempMinInit = map.get(dateDuJour)[1];

        if (tempMax > tempMaxInit) {
          tempMaxInit = tempMax;
        }
        if (tempMin < tempMinInit) {
          tempMinInit = tempMin;
        }
        map.put(dateDuJour, new Double[] {tempMaxInit, tempMinInit});
      }
    }

    LinkedHashMap<String, Double[]> finalMap = new LinkedHashMap<>();
    map.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEachOrdered(x -> finalMap.put(x.getKey(), x.getValue()));

    int i = 0;

    TempMinMax[] temperatureMinMax = new TempMinMax[map.size()];
    for (Map.Entry<String, Double[]> entry : finalMap.entrySet()) {
      temperatureMinMax[i] =
          new TempMinMax(entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
      ++i;
    }

    weatherPrediction.setTemperatureMinMax(temperatureMinMax);
  }
}
