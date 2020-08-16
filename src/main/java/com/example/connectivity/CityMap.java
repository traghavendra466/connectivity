package com.example.connectivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * CityMap class reads given file and load cities into a Map. 
 */
@Component
public class CityMap {

	private final Log LOG = LogFactory.getLog(CityMap.class);

	@Autowired
	private ResourceLoader resourceLoader;

	private Map<String, City> cityRoadMap = new HashMap<>();

	@Value("${data.file:classpath:city.txt}")
	private String fileName;

	public Map<String, City> getCityRoadMap() {
		return cityRoadMap;
	}

	@PostConstruct
	private void load() throws IOException {

		LOG.info("Loading data from file started ! ");

		InputStream is = null;
		Resource resource = resourceLoader.getResource(fileName);

		if (!resource.exists()) {
			// reading data from file system
			is = new FileInputStream(new File(fileName));
		} else {
			// reading data from classpath
			is = resource.getInputStream();
		}

		Scanner scanner = new Scanner(is);
		try(scanner){
			while (scanner.hasNext()) {
	
				String line = scanner.nextLine();
				if (StringUtils.isEmpty(line))
					continue;
	
				LOG.info(line);
	
				String[] split = line.split(",");
				String Akey = split[0].trim().toLowerCase();
				String Bkey = split[1].trim().toLowerCase();
	
				if (!Akey.equals(Bkey)) {
					City A = cityRoadMap.getOrDefault(Akey, City.build(Akey));
					City B = cityRoadMap.getOrDefault(Bkey, City.build(Bkey));
	
					A.addAdjacentCity(B);
					B.addAdjacentCity(A);
	
					cityRoadMap.put(A.getName(), A);
					cityRoadMap.put(B.getName(), B);
				}
			}
		}
		
		LOG.info("City Raod Map: " + cityRoadMap);
	}

	public City getCity(String name) {
		return cityRoadMap.get(name);
	}

}