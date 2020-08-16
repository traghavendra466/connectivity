package com.example.connectivity;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConnectivityApplication {
	private final Log LOG = LogFactory.getLog(ConnectivityApplication.class);

	@Autowired
	CityMap cityMap;

	public static void main(String[] args) {
		SpringApplication.run(ConnectivityApplication.class, args);
	}

	@GetMapping(value = "/connected", produces = "text/plain")
	public String isConnected(@RequestParam String origin, @RequestParam String destination) {

		LOG.info("URL '/connected' is requested!");
		LOG.info("Requested route between, origin: " + origin + ", destination: " + destination);
		City originCity = cityMap.getCity(origin.toLowerCase());
		City destCity = cityMap.getCity(destination.toLowerCase());

		try {
			Objects.requireNonNull(originCity);
			Objects.requireNonNull(destCity);
		}
		catch(NullPointerException ex) {
			LOG.error("Not a valid city name.");
			throw new NullPointerException();
		}
		return Route.routeExists(originCity, destCity) ? "yes" : "no";
	}
	
	@ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidCityError() {
        return "Either origin or destination city name is invalid";
    }
}
