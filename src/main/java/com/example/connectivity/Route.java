package com.example.connectivity;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Route {

	private static final Log LOG = LogFactory.getLog(Route.class);

	private Route() {
	}

	/**
	 * Find if destination city is reachable from origin.
	 * 
	 * @param origin      the origin
	 * @param destination the destination
	 * @return true if cities are connected
	 */
	public static boolean routeExists(City origin, City destination) {

		LOG.info("Origin: " + origin.getName() + ", destination: " + destination.getName());

		if (origin.equals(destination))
			return true;

		if (origin.getAdjacentCities().contains(destination))
			return true;

		Set<City> visited = new HashSet<>(Collections.singleton(origin));

		Deque<City> toBeVistedlist = new ArrayDeque<>(origin.getAdjacentCities());

		while (!toBeVistedlist.isEmpty()) {

			City city = toBeVistedlist.getLast();

			if (city.equals(destination))
				return true;

			// first time visit?
			if (!visited.contains(city)) {

				visited.add(city);

				// add adjacent cities to the to be visited list and
				// remove already visited cities from the to be visited list
				toBeVistedlist.addAll(city.getAdjacentCities());
				toBeVistedlist.removeAll(visited);

				LOG.info("Visiting: [" + city.getName() + "] , adjacent cities: [" + (city.prettyPrint())
						+ "], To Be Visited: [" + toBeVistedlist.toString() + "]");
			} else {
				// the city has been visited, so remove it from the bucket list
				toBeVistedlist.removeAll(Collections.singleton(city));
			}
		}

		return false;
	}
}