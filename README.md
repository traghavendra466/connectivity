# connectivity
Find if cities are connected!!

#### This is a Spring-Boot application, it determines if two cities are connected.

Two cities are considered to be connected if there’s a series of routes/roads that can be traveled from one city to another.

List of roads is available in a file. The file contains a list of city pairs (one pair per line, comma separeated), whih indicates there is a road between those cities. A sample list of routes are available in a project file `city.txt` located under the `resource` directory. 

This application responds with 'yes' if city1 is connected to city2, 'no' if city1 is not connected to city2. Any unexpected input results in a 'no' response.

### Build from source
```bash
    mvn clean install
```
### Run the application

Using maven Spring Boot plugin 
``` 
    mvn spring-boot:run 
```
Using Java command line 
```
    java -jar target/connectivity-0.0.1.jar
```

### Play with it

Example `Boston` and `Albany` _are not_ connected:

[http://localhost:8080/connected?origin=Boston&destination=Albany](http://localhost:8080/connected?origin=Boston&destination=Albany) (result **no**)

Example `Boston` and `New york` _are_ connected:

[http://localhost:8080/connected?origin=Boston&destination=New york](http://localhost:8080/connected?origin=Boston&destination=New york) (result **yes**)


### Provide your own roadmap file

Using maven Spring Boot plugin 
``` 
    mvn spring-boot:run -Ddata.file=/roadmap.txt 
```
Using Java command line 
```
    java -Ddata.file=/roadmap.txt -jar target/connectivity-0.0.1.jar
   
```

Source of this project also includes a Well-written Unit Tests to cover all the scenarios.
