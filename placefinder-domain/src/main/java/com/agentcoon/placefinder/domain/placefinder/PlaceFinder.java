package com.agentcoon.placefinder.domain.placefinder;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationService;
import com.agentcoon.placefinder.domain.geolocation.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    public List<FacebookPlace> findPlaces(String country, String city, String searchString) throws GeoLocationException {

        List<Location> locations = geoLocationService.getLocations(country, city);

        if (locations.isEmpty()) {
            logger.info("No location {}, {} was found.", city, country);
            throw new NotFoundException("Specified location was not found.");
        }

        List<FacebookPlace> facebookPlaces = new ArrayList<>();

        locations.forEach(location -> {

            List<FacebookPlace> places = new ArrayList<>();

            try {
                places = facebookGateway.search(location.getLatitude(),
                        location.getLongitude(), location.calculateRadius(), searchString);
            } catch (FacebookGatewayException e) {
                logger.error("An exception occurred while searching for places in {}, {}", city, country, e);
            }

            facebookPlaces.addAll(places);
        });

        return facebookPlaces.stream().distinct().collect(toList());
    }
}
