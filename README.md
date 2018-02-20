# Place Finder

An application finding places of interest within an area around a specified city.

Integrated with following data sources:

* MapQuest Nominatim API for finding specified country and city

* Facebook API for finding places in the specified location

It's possible to extend the application to provide data from other sources, e.g. Google Maps API, to improve discoverability.


## API

### Documentation

The API is documented using [RAML](http://raml.org/) V1.
It's available after starting the application under http://localhost:8080/api


### Installing dependencies

#### Java
From [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
JDK 8 is required to run application

#### Maven
[Apache Maven 3.x](http://maven.apache.org/download.cgi)


### Running application

Application runs as a standard Java program with a main method. Jetty is embedded so there's no need to run as a WAR.

module: placefinder-app  

main class: com.agentcoon.placefinder.app.dropwizard.PlacefinderApplication

program arguments: server ${workspace_loc:/placefinder-configuration}/src/main/resources/local/placefinder.yml

Application is running under http://localhost:8080.

## Build

Using Maven from top level of project
```
mvn clean package verify
```