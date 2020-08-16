package com.example.connectivity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

public class CityTest {

    @Test
    public void buildTest() {
        City city = City.build("New york");
        assertEquals("new york", city.getName());
    }

    @Test
    public void addAdjacentCitiesTest() {
        City city = City.build("Boston");
        city.addAdjacentCity(City.build("New york"))
                .addAdjacentCity(City.build("Providence"));

        assertEquals(2, city.getAdjacentCities().size());
    }

    @Test
    public void buildWithAdjacentCitiesTest() {
        City city = City.build("Boston");
        city.addAdjacentCity(City.build("New york"))
                .addAdjacentCity(City.build("Providence"));

        Set<City> adjacentCities = city.getAdjacentCities();
        assertEquals(2, adjacentCities.size());
    }
    
}