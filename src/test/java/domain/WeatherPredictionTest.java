package domain;

import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.domain.WeatherPrediction;
import com.weatherapp.domain.weather.TempMinMax;

public class WeatherPredictionTest {

	private WeatherPrediction weatherPrediction;
	private RestTemplate restTemplate;
	private MockRestServiceServer server;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();
	}

	@Test
	public void testMinTempCalculation() {
		server.expect(ExpectedCount.once(), requestTo("/testWeatherApp/1")).andExpect(method(GET))
				.andRespond(withSuccess(stringFromTestFile(1), APPLICATION_JSON));

		weatherPrediction = restTemplate.getForObject("/testWeatherApp/{id}", WeatherPrediction.class, 1);

		weatherPrediction.calcMinMax();
		TempMinMax resultatCalcMinMaxFirstDay = weatherPrediction.getTemperatureMinMax()[0];
		assertThat(resultatCalcMinMaxFirstDay.getDate(), equalTo("2017-03-10"));
		assertThat(resultatCalcMinMaxFirstDay.getMaxTemp(), equalTo(Double.valueOf("279.489")));
		assertThat(resultatCalcMinMaxFirstDay.getMinTemp(), equalTo(Double.valueOf("277.94")));

		TempMinMax resultatCalcMinMaxSecondDay = weatherPrediction.getTemperatureMinMax()[1];
		assertThat(resultatCalcMinMaxSecondDay.getDate(), equalTo("2017-03-11"));
		assertThat(resultatCalcMinMaxSecondDay.getMaxTemp(), equalTo(Double.valueOf("287.998")));
		assertThat(resultatCalcMinMaxSecondDay.getMinTemp(), equalTo(Double.valueOf("274.326")));
	}

	private String stringFromTestFile(Integer numeroDeTest) {

		String content = "";
		try {
			content = new String(
					readAllBytes(Paths.get("src/test/resources/weatherprediction/data_" + numeroDeTest + ".txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
