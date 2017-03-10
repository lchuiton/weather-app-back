package domain;

import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.domain.WeatherPrediction;
import com.weatherapp.domain.weather.List;

public class WeatherPredictionTest {

	private WeatherPrediction weatherPrediction;
	private List[] list;
	private RestTemplate restTemplate;
	private MockRestServiceServer server;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();

	}

	@Test
	public void testWithNoData() {
		// JSONObject jsonObj = new JSONObject(arg0)
		server.expect(manyTimes(), requestTo("/testWeatherApp/1")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(stringFromFile(1), MediaType.APPLICATION_JSON));

		weatherPrediction = restTemplate.getForObject("/testWeatherApp/{id}", WeatherPrediction.class, 1);

		// org.hamc"Should return empty when there is no data",
		org.hamcrest.MatcherAssert.assertThat(weatherPrediction.getList().length, Matchers.is(Matchers.equalTo(0)));
	}

	private String stringFromFile(Integer numeroDeTest) {

		String content = "";
		try {
			content = new String(Files
					.readAllBytes(Paths.get("src/test/resources/weatherprediction/data_" + numeroDeTest + ".txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
}
