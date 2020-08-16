package com.example.connectivity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * City Class represents a City along with the adjacent cities
 */
public class City {
	private String name;

	private Set<City> adjacentCities = new HashSet<>();

	private City(String name) {
		// City name cannot be null
		Objects.requireNonNull(name);
		this.name = name.trim().toLowerCase();
	}

	private City() {
	}

	public static City build(String name) {
		return new City(name);
	}

	@Override
	public String toString() {

		return "City{" + "name='" + name + "'" + ", adjacent cities ='" + prettyPrint() + "'}";
	}

	public String prettyPrint() {
		return adjacentCities.stream().map(City::getName).collect(Collectors.joining(", "));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City addAdjacentCity(City city) {
		adjacentCities.add(city);
		return this;
	}

	public Set<City> getAdjacentCities() {
		return adjacentCities;
	}

}