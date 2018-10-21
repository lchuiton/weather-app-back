package com.weatherapp.ws;

import com.weatherapp.config.BusinessConfigurationProperties;
import com.weatherapp.model.WeatherPrediction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    ApiWeatherWebServiceTest.TestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
public class ApiWeatherWebServiceTest {

  @Autowired
  private
  ApiWeatherWebService apiWeatherWebService;

  @MockBean
  private
  RestTemplate restTemplate;
  @MockBean
  private
  BusinessConfigurationProperties properties;

  @Before
  public void setup() {
    Mockito.when(properties.getUri()).thenReturn("http://test.com");
  }

  @Test(expected = NullPointerException.class)
  public void test() {
    // Arrange
    ResponseEntity<WeatherPrediction> response = new ResponseEntity<>(HttpStatus.OK);
    Mockito.when(restTemplate.exchange(null, HttpMethod.GET, null, WeatherPrediction.class))
        .thenReturn(response);

    // Act
    apiWeatherWebService.getPredictionByCityAndCountry("", "");

    // Assert
  }

  @Configuration
  static class TestConfig {

    @Bean
    ApiWeatherWebService apiWeatherWebService() {
      return new ApiWeatherWebService();
    }

  }
}
