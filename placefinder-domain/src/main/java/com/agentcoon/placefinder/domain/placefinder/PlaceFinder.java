package com.agentcoon.placefinder.domain.placefinder;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationService;
import com.agentcoon.placefinder.domain.geolocation.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PlaceFinder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GeoLocationService geoLocationService;
    private final FacebookGateway facebookGateway;

    @Inject
    public PlaceFinder(GeoLocationService geoLocationService, FacebookGateway facebookGateway) {
        this.geoLocationService = geoLocationService;
        this.facebookGateway = facebookGateway;
    }

    public Map<String, List<FacebookPlace>> findPlaces(String country, String city, String searchString) throws GeoLocationException, NotFoundException {

        List<Location> locations = geoLocationService.getLocations(country, city);

        if (locations.isEmpty()) {
            logger.info("No location {}, {} was found.", city, country);
            throw new NotFoundException("Specified location was not found.");
        }

        Map<String, List<FacebookPlace>> placesPerLocation = new HashMap<>();

        locations.forEach(location -> {
            List<FacebookPlace> foundPlaces = getFacebookPlacesForLocation(location, searchString);

            placesPerLocation.put(location.getDisplayName(), filterPlacesInNearbyCities(foundPlaces, city));
        }
    );

        return placesPerLocation;
    }

    private List<FacebookPlace> getFacebookPlacesForLocation(Location location, String searchString) {
        List<FacebookPlace> places = new ArrayList<>();

        try {
            places = facebookGateway.searchPlaces(location.getLatitude(),
                    location.getLongitude(), location.calculateRadius(), searchString);
        } catch (FacebookGatewayException e) {
            logger.warn("An exception occurred while searching for {} places in {}.", searchString, location.getDisplayName());
        }
        return places;
    }

    private List<FacebookPlace> filterPlacesInNearbyCities(List<FacebookPlace> places, String city) {
        return places.stream().filter(e -> e.getCity().equalsIgnoreCase(city)).collect(toList());
    }
}
