package com.example.connectivity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ConnectivityApplicationTests {

	@Autowired
	CityMap cityMap;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void loadCitiesTest() {
		assertFalse(cityMap.getCityRoadMap().isEmpty(), "Loading cities file failed");
	}

	@Test
	public void sameCity() {
		City city = City.build("Boston");
		assertTrue(Route.routeExists(city, city));
	}

	@Test
	public void adjacenctCities() {
		City cityA = cityMap.getCity("trenton");
		City cityB = cityMap.getCity("albany");

		assertTrue(Route.routeExists(cityA, cityB));
	}

	@Test
	public void restConnectedTest() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "New york");
		params.put("destination", "Boston");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class,
				params);
		assertEquals("yes", body);
	}

	@Test
	public void restNotConnectedIT() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "New york");
		params.put("destination", "Albany");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class,
				params);
		assertEquals("no", body);
	}

	@Test
	public void badRequestTest() {
		ResponseEntity<String> response = restTemplate.exchange("/connected?origin=&destination=", HttpMethod.GET,
				HttpEntity.EMPTY, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
}
