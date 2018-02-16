# Place Finder


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

parameters: server ${workspace_loc:/placefinder-configuration}/src/main/resources/local/placefinder.yml


## Build

Using Maven from top level of project
```
mvn clean package verify
```