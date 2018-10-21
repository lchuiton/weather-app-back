package domain;

import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.weatherapp.helper.TemperatureHelper;
import com.weatherapp.model.Temperature;
import com.weatherapp.model.WeatherPrediction;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public class WeatherPredictionTest {

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
    server.expect(ExpectedCount.once(), requestTo("/testWeatherApp/1")).andExpect(method(GET))
        .andRespond(withSuccess(stringFromTestFile(1), APPLICATION_JSON));

    WeatherPrediction weatherPrediction = restTemplate
        .getForObject("/testWeatherApp/{id}", WeatherPrediction.class, 1);

    // Act
    List<Temperature> resultMin = temperatureHelper.calculateMinimumTemperature(weatherPrediction);
    List<Temperature> resultMax = temperatureHelper.calculateMaximumTemperature(weatherPrediction);

    // Assert
    Temperature resultCalcMinMaxFirstDay = resultMin.get(0);
    assertThat(resultCalcMinMaxFirstDay.getDate(), equalTo("2017-03-10"));
    assertThat(resultCalcMinMaxFirstDay.getTemp(), equalTo(Double.valueOf("277.94")));

    Temperature resultCalcMaxFirstDay = resultMax.get(0);
    assertThat(resultCalcMaxFirstDay.getDate(), equalTo("2017-03-10"));
    assertThat(resultCalcMaxFirstDay.getTemp(), equalTo(Double.valueOf("279.489")));

    Temperature resultCalcMinMaxSecondDay = resultMin.get(1);
    assertThat(resultCalcMinMaxSecondDay.getDate(), equalTo("2017-03-11"));
    assertThat(resultCalcMinMaxSecondDay.getTemp(), equalTo(Double.valueOf("274.326")));

    Temperature resultCalcMaxSecondDay = resultMax.get(1);
    assertThat(resultCalcMaxSecondDay.getDate(), equalTo("2017-03-11"));
    assertThat(resultCalcMaxSecondDay.getTemp(), equalTo(Double.valueOf("287.998")));
  }

  private String stringFromTestFile(Integer numeroDeTest) {

    String content = "";
    try {
      content = new String(
          readAllBytes(
              Paths.get("src/test/resources/weatherprediction/data_" + numeroDeTest + ".txt")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }
}
