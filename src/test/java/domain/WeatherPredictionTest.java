package domain;

import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.model.TempMinMax;
import com.weatherapp.model.WeatherPrediction;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class WeatherPredictionTest {

  private WeatherPrediction weatherPrediction;
  private RestTemplate restTemplate;
  private TemperatureHelper temperatureHelper;

  private MockRestServiceServer server;

  @Before
  public void setup() {
    restTemplate = new RestTemplate();
    temperatureHelper = new TemperatureHelper();
    server = MockRestServiceServer.bindTo(restTemplate).build();
  }

  @Test
  public void testMinTempCalculation() {
    // Arrange
    server
        .expect(ExpectedCount.once(), requestTo("/testWeatherApp/1"))
        .andExpect(method(GET))
        .andRespond(withSuccess(stringFromTestFile(1), APPLICATION_JSON));

    weatherPrediction =
        restTemplate.getForObject("/testWeatherApp/{id}", WeatherPrediction.class, 1);

    // Act
    if (weatherPrediction == null) {
      Assertions.fail("WeatherPrediction cannot be null, check the test cases");
    }
    temperatureHelper.calcMinMax(weatherPrediction);
    TempMinMax resultCalcMinMaxFirstDay = weatherPrediction.getTemperatureMinMax()[0];

    // Assert
    MatcherAssert.assertThat(resultCalcMinMaxFirstDay.getDate(), equalTo("2017-03-10"));
    MatcherAssert.assertThat(
        resultCalcMinMaxFirstDay.getMaxTemp(), equalTo(Double.valueOf("279.489")));
    MatcherAssert.assertThat(
        resultCalcMinMaxFirstDay.getMinTemp(), equalTo(Double.valueOf("277.94")));

    TempMinMax resultCalcMinMaxSecondDay = weatherPrediction.getTemperatureMinMax()[1];
    MatcherAssert.assertThat(resultCalcMinMaxSecondDay.getDate(), equalTo("2017-03-11"));
    MatcherAssert.assertThat(
        resultCalcMinMaxSecondDay.getMaxTemp(), equalTo(Double.valueOf("287.998")));
    MatcherAssert.assertThat(
        resultCalcMinMaxSecondDay.getMinTemp(), equalTo(Double.valueOf("274.326")));
  }

  private String stringFromTestFile(Integer testNumber) {

    String content = "";
    try {
      content =
          new String(
              readAllBytes(
                  Paths.get("src/test/resources/weatherprediction/data_" + testNumber + ".txt")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }
}
